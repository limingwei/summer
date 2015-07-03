package cn.limw.summer.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import cn.limw.summer.java.collection.NiceToStringMap;
import cn.limw.summer.util.Files;

/**
 * @author li
 * @version 1 (2015年1月21日 上午10:18:00)
 * @since Java7
 */
@SuppressWarnings("unchecked")
public class Request implements Serializable {
    private static final long serialVersionUID = 2822594744919040749L;

    public static final String GET = "GET", POST = "POST", PUT = "PUT", DELETE = "DELETE", UTF8 = "UTF-8", FORM_URLENCODED = "application/x-www-form-urlencoded; charset=utf-8";

    private String url = "", urlWithParameters, method = null;

    private Map<String, List<String>> parameters = new HashMap<String, List<String>>();

    private Map<String, List<String>> headers = new HashMap<String, List<String>>();

    private Map<String, List<Object>> fields = new NiceToStringMap(new HashMap<String, List<Object>>());

    private byte[] data;// 请求体数据

    private ByteArrayOutputStream outputStream;

    private Integer connectTimeout = Integer.parseInt(System.getProperty("sun.net.client.defaultConnectTimeout", (5 * 60 * 1000) + ""));// 默认五分钟连接超时

    private Integer readTimeout = Integer.parseInt(System.getProperty("sun.net.client.defaultReadTimeout", (5 * 60 * 1000) + ""));// 默认五分钟读取超时

    private Boolean followRedirects = HttpURLConnection.getFollowRedirects();// 默认自动跳转30x

    private Proxy proxy = Proxy.NO_PROXY;// 默认无代理

    private Boolean hasMultipartField = null;

    /**
     * 设置请求地址
     */
    public Request setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUrl() {
        return url;
    }

    /**
     * GET POST
     */
    public Request setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getMethod() {
        return method;
    }

    private String getMethodForExecute() {
        if (null == getMethod()) {
            return guessMethod();
        }
        return getMethod();
    }

    private String guessMethod() {
        if (null != data || null != outputStream) {
            return POST;
        }
        if (!this.fields.isEmpty()) {
            return POST;
        }
        return GET;
    }

    public Map<String, List<String>> getHeaders() {
        return this.headers;
    }

    /**
     * 获取单个Header的值
     */
    public String getHeader(String key) {
        List<String> header = this.headers.get(key);
        return null == header ? null : header.get(0);
    }

    public Request setHeader(String key, String value) {
        this.headers.put(key, Arrays.asList(value));
        return this;
    }

    public Request setReferer(String referer) {
        this.setHeader("Referer", referer);
        return this;
    }

    public Request setUserAgent(String userAgent) {
        this.setHeader("User-Agent", userAgent);
        return this;
    }

    public Request setContentType(String contentType) {
        this.setHeader("Content-Type", contentType);
        return this;
    }

    /**
     * 是否跳转30x 可通过 HttpURLConnection.setFollowRedirects(true)进行全局设置
     */
    public Request setFollowRedirects(Boolean followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }

    /**
     * 设置代理 可通过 System.setProperty("http.proxyHost", "208.110.94.187");
     * System.setProperty("http.proxyPort", "8089");
     * Authenticator.setDefault(authenticator); 进行全局设置
     */
    public Request setProxy(Proxy proxy) {
        this.proxy = null == proxy ? Proxy.NO_PROXY : proxy;
        return this;
    }

    /**
     * 链接超时，单位毫秒，默认 5 * 60 * 1000 五分钟 可通过
     * System.setProperty("sun.net.client.defaultConnectTimeout", 1000)进行全局设置
     */
    public Request setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    /**
     * 读取超时，单位毫秒，默认 5 * 60 * 1000 五分钟 可通过
     * System.setProperty("sun.net.client.defaultReadTimeout", 1000)进行全局设置
     */
    public Request setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    /**
     * 设置Cookie
     */
    public Request setCookies(List<HttpCookie> cookies) {
        this.setCookies(Util.cookieToString(cookies));
        return this;
    }

    /**
     * 通过字符串设置Cookies
     */
    public Request setCookies(String cookies) {
        this.setHeader("Cookie", cookies);
        return this;
    }

    /**
     * 通过字符串添加Cookies
     */
    public Request addCookies(String cookies) {
        cookies = cookies + (null == this.getCookies() ? "" : ("; " + this.getCookies()));
        this.setHeader("Cookie", cookies);
        return this;
    }

    /**
     * 返回字符串的RequestCookie
     */
    public String getCookies() {
        return this.getHeader("Cookie");
    }

    /**
     * 返回一个Cookie值
     */
    public String getCookie(String key) {
        return CookieStore.getCookie(this.getCookies(), key);
    }

    /**
     * 设置GET访问的参数,会添加到url,url中不应当已经存在
     */
    public Request setParameters(Map<Object, Object> map) {
        for (Entry<Object, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (null != value && value.getClass().isArray()) {//数组
                value = Arrays.asList((Object[]) value);
            } else if (null != value && value instanceof Set) {//Set
                value = new ArrayList((Set) value);
            } else if (!(value instanceof List)) {//单个对象
                value = Arrays.asList(value);
            }
            this.parameters.put(entry.getKey() + "", (List<String>) value);//List
        }
        return this;
    }

    /**
     * 如果有同名项,会被替换
     */
    public Request setParameter(String key, Object value) {
        if (value instanceof List) {
            this.parameters.put(key, (List) value);
        } else {
            List<String> para = new ArrayList<String>();
            para.add(value + "");
            this.parameters.put(key, para);
        }
        return this;
    }

    /**
     * 添加,不替换重名项
     */
    public Request addParameter(String key, Object value) {
        List<String> parameter = this.parameters.get(key);
        if (null == parameter) {
            this.setParameter(key, value);
        } else {
            parameter.add(value + "");
        }
        return this;
    }

    /**
     * 设置POST请求的表单域
     */
    public Request setFields(Map<String, Object> fields) {
        for (Entry<String, Object> entry : fields.entrySet()) {
            Object value = entry.getValue();
            if (!(value instanceof List)) {
                value = Arrays.asList(value);
            }
            this.fields.put(entry.getKey(), (List<Object>) value);
        }
        return this;
    }

    /**
     * 会替换同名项
     */
    public Request setField(String key, Object value) {
        if (value instanceof List) {
            this.fields.put(key, (List) value);
        } else {
            this.fields.put(key, new ArrayList<Object>(Arrays.asList(value)));
        }
        return this;
    }

    /**
     * 添加,不会替换重名项
     */
    public Request addField(String key, Object value) {
        List<Object> field = this.fields.get(key);
        if (null == field) {
            this.setField(key, value);
        } else {
            field.add(value);
        }
        return this;
    }

    /**
     * 执行HTTP请求,返回Response
     */
    public Response execute() {
        return this.execute(new Response());
    }

    /**
     * 一个辅助方法，执行请求，会自动保持Cookie
     */
    public final Response execute(CookieStore cookieStore) {
        this.setCookies(cookieStore.getCookies());
        Response response = this.execute();
        cookieStore.addAll(response.getCookies());
        return response;
    }

    /**
     * 真正发起的GET或者POST请求，会将Cookie转移到Response
     */
    public final <T extends Response> T execute(T response) {
        try {
            String url = this.getUrlForExecute();
            byte[] data = this.getData();
            HttpURLConnection connection = (HttpURLConnection) openConnection(url);
            connection.setUseCaches(false);// 不使用缓存
            connection.setRequestMethod(getMethodForExecute());
            connection.setReadTimeout(this.readTimeout);
            connection.setConnectTimeout(this.connectTimeout);
            connection.setInstanceFollowRedirects(this.followRedirects);
            for (Entry<String, List<String>> entry : getHeaders().entrySet()) {
                for (String value : entry.getValue()) {
                    connection.addRequestProperty(entry.getKey(), value);
                }
            }
            boolean isPostOrPut = POST.equalsIgnoreCase(getMethodForExecute()) || PUT.equalsIgnoreCase(getMethodForExecute());
            connection.setDoOutput(isPostOrPut);// post时设置为true
            connection.connect();
            if (isPostOrPut && null != data) {
                connection.getOutputStream().write(data);// post时向服务器写数据
            }

            response.setRequest(this);
            response.setStatus(connection.getResponseCode());
            response.setHeaders(connection.getHeaderFields());

            InputStream inputStream = connection.getInputStream();
            InputStream errorStream = connection.getErrorStream();
            response.setData(Files.toBytes(200 == response.getStatus() ? inputStream : errorStream));
            Files.close(inputStream);
            Files.close(errorStream);

            String responseCookies = CookieStore.getCookies(connection.getHeaderFields());// responseCookies
            String cookies = null == this.getCookies() ? "" : (this.getCookies() + (null == responseCookies ? "" : responseCookies));
            response.setCookies(Util.stringToCookie(cookies));// 合并requestCookies和responseCookies
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e.toString() + ", url=" + getUrlForExecute() + ", method=" + getMethodForExecute(), e);
        }
    }

    /**
     * 创建 HttpURLConnection , 判断是否 NO_PROXY
     */
    private URLConnection openConnection(String url) throws IOException, MalformedURLException {
        if (Proxy.NO_PROXY.equals(this.proxy)) {
            return new URL(url).openConnection();
        } else {
            return new URL(url).openConnection(this.proxy);
        }
    }

    /**
     * 获取请求地址,此处已添加parameters内容
     */
    private final String getUrlForExecute() {
        if (null == this.urlWithParameters) {
            if (null == this.url || this.url.trim().isEmpty()) {
                throw new IllegalArgumentException("url 为空");
            }
            String url = this.url;
            if (!url.startsWith("http://") && !url.startsWith("https://")) {// 判断添加http
                url = "http://" + url;
            }
            url += url.contains("?") ? "&" : "?";
            for (Entry<String, List<String>> entry : this.parameters.entrySet()) {
                for (Object value : entry.getValue()) {
                    url += entry.getKey() + "=" + value + "&";
                }
            }
            this.urlWithParameters = url.substring(0, url.length() - 1);
        }
        return this.urlWithParameters;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * 若Fields里面包括File或URL则使用getDataMultipart
     */
    protected byte[] getData() throws Exception {
        if (null != data) {
            return this.data;
        } else if (null != outputStream) {
            return outputStream.toByteArray();
        } else if (hasMultipartField()) { // 有至少一个文件
            return this.getDataMultipart();
        } else {//文本表单
            this.setContentType(FORM_URLENCODED);
            return this.getDataUrlEncoded();
        }
    }

    /**
     * 有文件域
     */
    private Boolean hasMultipartField() {
        if (null == hasMultipartField) {
            for (Entry<String, List<Object>> entry : this.fields.entrySet()) {
                for (Object each : entry.getValue()) {
                    if (Util.isMultipartField(each)) {// 本地文件或远程文件
                        hasMultipartField = true; // 有至少一个文件
                        return true;
                    }
                }
            }
            hasMultipartField = false;
        }
        return hasMultipartField;
    }

    /**
     * 获取fields内容,这个只支持文本域
     */
    protected final byte[] getDataUrlEncoded() {
        String data = "";
        for (Entry<String, List<Object>> entry : this.fields.entrySet()) {
            for (Object value : entry.getValue()) {
                String key = entry.getKey();
                if (null != value && value instanceof Object[]) {
                    Object[] array = (Object[]) value;
                    for (Object each : array) {
                        data += (data.isEmpty() ? "" : "&") + key + "=" + Util.toString(each);
                    }
                } else {
                    data += (data.isEmpty() ? "" : "&") + key + "=" + Util.toString(value);
                }
            }
        }
        return data.getBytes();
    }

    /**
     * 获取fields内容,支持文本域和文件(File,URL),注意,这不是一个无损方法,会setContentType
     */
    protected final byte[] getDataMultipart() throws Exception {
        String FORM_BOUNDARY = "--------------------FormBoundary-----" + UUID.randomUUID();// 分隔符,FormBoundary
        this.setContentType("multipart/form-data; boundary=" + FORM_BOUNDARY);// 必须要的,这里不是无损的
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (Entry<String, List<Object>> entry : this.fields.entrySet()) {
            for (Object each : entry.getValue()) {
                byteArrayOutputStream.write(("--" + FORM_BOUNDARY + "\r\n").getBytes());// 每个域的开头
                if (Util.isMultipartField(each)) { // 本地文件或远程文件
                    byteArrayOutputStream.write(("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"; filename=\"" + Util.fileName(each) + "\"" + "\r\n").getBytes());// 文件域头
                    byteArrayOutputStream.write(("Content-Type: " + Util.contentType(each) + "\r\n" + "\r\n").getBytes());
                    byteArrayOutputStream.write(Util.multipartFieldToByteArray(each));// 文件内容
                } else { // 其他，全按照字符串处理
                    byteArrayOutputStream.write(("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + "\r\n").getBytes());// 表单头
                    byteArrayOutputStream.write(("\r\n" + each).getBytes());// 表单值
                }
                byteArrayOutputStream.write("\r\n".getBytes());// 换行
            }
        }
        byteArrayOutputStream.write(("--" + FORM_BOUNDARY + "--\r\n").getBytes());// 所有域的结尾
        return byteArrayOutputStream.toByteArray();
    }

    public OutputStream getOutputStream() {
        this.outputStream = new ByteArrayOutputStream();
        return this.outputStream;
    }

    public String toString() {
        return super.toString() + ", url=" + getUrl() + ", method=" + getMethod() + ", fields=" + fields;
    }
}