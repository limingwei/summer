package cn.limw.summer.http;

import java.io.Serializable;
import java.net.HttpCookie;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * HTTP响应
 * @author 明伟
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 8729025618463464379L;

    private Request request;

    /**
     * 正文类容
     */
    private byte[] data;

    /**
     * 正文文本
     */
    private String body;

    /**
     * 包括RequestcCookies和ResponseCookies
     */
    private List<HttpCookie> cookies;

    private Map<String, List<String>> headers;

    private Integer status;

    public void setRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    /**
     * 设置Cookie，在request里有使用
     */
    public void setCookies(List<HttpCookie> cookies) {
        this.cookies = cookies;
    }

    /**
     * 返回Cookie列表
     */
    public List<HttpCookie> getCookies() {
        return this.cookies;
    }

    public HttpCookie getCookie(String name) {
        for (HttpCookie cookie : this.cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 获取response返回的正文类容
     */
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * 文本形式的response正文
     */
    public String getBody() {
        if (null == this.body) {
            this.body = new String(this.getData(), Charset.forName("UTF-8"));
        }
        return this.body;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    //    public Map<String, List<String>> getHeaders() {
    //        if (null == this.headers) {
    //            this.headers = this.getHttpURLConnection().getHeaderFields();
    //        }
    //        return this.headers;
    //    }

    public String getHeader(String name) {
        if (null != this.getHeaders()) {
            List<String> temp = this.getHeaders().get(name);
            if (null != temp && !temp.isEmpty()) {
                return temp.get(0).toString();
            }
        }
        return null;
    }

    /**
     * getResponseCode
     */
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String toString() {
        if (getStatus() == 200) {
            return getBody();
        }
        return "status=" + getStatus() + ", url=" + getRequest().getUrl() + ", method=" + getRequest().getMethod();
    }
}