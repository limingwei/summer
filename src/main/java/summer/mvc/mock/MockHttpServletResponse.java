package summer.mvc.mock;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import summer.log.Logger;
import summer.util.Log;

/**
 * MockHttpServletResponse
 * 
 * @author li (limingwei@mail.com)
 * @version 0.1.1 (2012-09-27)
 */
public class MockHttpServletResponse extends MockServletResponse implements HttpServletResponse {
    private static final Logger log = Log.slf4j();

    private Integer status;

    public void sendRedirect(String path) throws IOException {
        log.debug("rederect to : " + path);
    }

    public void setStatus(int status) {
        this.status = status;
        log.debug("set status " + status);
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status, String arg1) {
        setStatus(status);
    }

    public void addCookie(Cookie cookie) {}

    public void addDateHeader(String arg0, long arg1) {}

    public void setIntHeader(String arg0, int arg1) {}

    public void addHeader(String key, String value) {}

    public void setHeader(String arg0, String arg1) {}

    public void addIntHeader(String key, int value) {}

    public void setDateHeader(String arg0, long arg1) {}

    public boolean containsHeader(String arg0) {
        return false;
    }

    public String getHeader(String key) {
        return null;
    }

    public Collection<String> getHeaders(String arg0) {
        return null;
    }

    public Collection<String> getHeaderNames() {
        return null;
    }

    public String encodeRedirectURL(String arg0) {
        return null;
    }

    public String encodeRedirectUrl(String arg0) {
        return null;
    }

    public String encodeURL(String arg0) {
        return null;
    }

    public String encodeUrl(String arg0) {
        return null;
    }

    public void sendError(int status) throws IOException {}

    public void sendError(int status, String arg1) throws IOException {}
}