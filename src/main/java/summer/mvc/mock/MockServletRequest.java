package summer.mvc.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * MockServletRequest
 * @author li (limingwei@mail.com)
 * @version 0.1.1 (2012-09-27)
 */
public class MockServletRequest implements ServletRequest {
    private MockServletContext servletContext;

    private Map<String, Object> requestMap;

    private Map<String, String[]> parameterMap;

    private String encoding;

    private String contentType;

    public MockServletRequest() {
        this.requestMap = new HashMap<String, Object>();
        this.parameterMap = new HashMap<String, String[]>();
    }

    public void setServletContext(MockServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public MockRequestDispatcher getRequestDispatcher(String path) {
        return new MockRequestDispatcher(path);
    }

    public MockServletContext getServletContext() {
        return this.servletContext;
    }

    public void removeAttribute(String key) {
        requestMap.remove(key);
    }

    public void setAttribute(String key, Object value) {
        requestMap.put(key, value);
    }

    public Object getAttribute(String key) {
        return requestMap.get(key);
    }

    public Enumeration<String> getAttributeNames() {
        return new Vector<String>(requestMap.keySet()).elements();
    }

    public String getParameter(String key) {
        String[] params = parameterMap.get(key);
        return null == params ? null : params[0];
    }

    public Map<String, String[]> getParameterMap() {
        return this.parameterMap;
    }

    public Enumeration<String> getParameterNames() {
        return new Vector<String>(parameterMap.keySet()).elements();
    }

    public String[] getParameterValues(String key) {
        return parameterMap.get(key);
    }

    public void setParameter(Map<String, String[]> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public void setParameter(String key, String value) {
        parameterMap.put(key, new String[] { value });
    }

    public void setParameter(String key, String[] value) {
        parameterMap.put(key, value);
    }

    public String getCharacterEncoding() {
        return this.encoding;
    }

    public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {
        this.encoding = encoding;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return 0;
    }

    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    public String getLocalAddr() {
        return null;
    }

    public String getLocalName() {
        return null;
    }

    public int getLocalPort() {
        return 0;
    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration<Locale> getLocales() {
        return null;
    }

    public String getProtocol() {
        return null;
    }

    public BufferedReader getReader() throws IOException {
        return null;
    }

    public String getRealPath(String path) {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public int getRemotePort() {
        return 0;
    }

    public String getScheme() {
        return null;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public boolean isAsyncStarted() {
        return false;
    }

    public boolean isAsyncSupported() {
        return false;
    }

    public boolean isSecure() {
        return false;
    }

    public long getContentLengthLong() {
        return 0;
    }

    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    public AsyncContext getAsyncContext() {
        return null;
    }

    public DispatcherType getDispatcherType() {
        return null;
    }
}