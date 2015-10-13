package summer.mvc.mock;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

/**
 * MockHttpServletRequest
 * @author li (limingwei@mail.com)
 * @version 0.1.1 (2012-09-27)
 */
public class MockHttpServletRequest extends MockServletRequest implements HttpServletRequest {
    private String method;

    private MockHttpSession session;

    private String servletPath;

    public MockHttpServletRequest() {
        this.method = "GET";
        this.session = new MockHttpSession(getServletContext());
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public String getServletPath() {
        return this.servletPath;
    }

    public String getQueryString() {
        return null;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }

    public MockHttpSession getSession() {
        MockHttpSession session = this.session;
        if (null == session.getServletContext()) {
            session.setServletContext(this.getServletContext());
        }
        return this.session;
    }

    public HttpSession getSession(boolean flag) {
        return this.session;
    }

    public int getIntHeader(String key) {
        return Integer.parseInt(getHeader(key));
    }

    public String getHeader(String key) {
        return null;
    }

    public Enumeration<String> getHeaderNames() {
        return null;
    }

    public Enumeration<String> getHeaders(String key) {
        return null;
    }

    public String getAuthType() {
        return null;
    }

    public String getContextPath() {
        return null;
    }

    public String getPathInfo() {
        return null;
    }

    public String getPathTranslated() {
        return null;
    }

    public String getRemoteUser() {
        return null;
    }

    public String getRequestURI() {
        return null;
    }

    public StringBuffer getRequestURL() {
        return null;
    }

    public String getRequestedSessionId() {
        return null;
    }

    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        return false;
    }

    public Cookie[] getCookies() {
        return null;
    }

    public long getDateHeader(String arg0) {
        return 0;
    }

    public Principal getUserPrincipal() {
        return null;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    public boolean isRequestedSessionIdValid() {
        return false;
    }

    public boolean isUserInRole(String arg0) {
        return false;
    }

    public void login(String username, String pawssword) throws ServletException {}

    public void logout() throws ServletException {}

    public String changeSessionId() {
        return null;
    }

    public Collection<Part> getParts() throws IOException, ServletException {
        return null;
    }

    public Part getPart(String name) throws IOException, ServletException {
        return null;
    }

    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        return null;
    }
}