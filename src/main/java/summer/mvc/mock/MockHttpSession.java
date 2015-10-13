package summer.mvc.mock;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import summer.log.Logger;
import summer.util.Log;

/**
 * MockHttpSession
 * @author li (limingwei@mail.com)
 * @version 0.1.1 (2012-09-27)
 */
@SuppressWarnings("deprecation")
public class MockHttpSession implements HttpSession {
    private static final Logger log = Log.slf4j();

    private ServletContext servletContext;

    private Map<String, Object> sessionMap;

    public MockHttpSession(ServletContext servletContext) {
        this.servletContext = servletContext;
        this.sessionMap = new HashMap<String, Object>();
    }

    public void removeAttribute(String key) {
        sessionMap.remove(key);
        log.debug("remove session " + key);
    }

    public void setAttribute(String key, Object value) {
        sessionMap.put(key, value);
        log.debug("set session " + key);
    }

    public ServletContext getServletContext() {
        return this.servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public Object getAttribute(String key) {
        return sessionMap.get(key);
    }

    public Object getValue(String key) {
        return sessionMap.get(key);
    }

    public String[] getValueNames() {
        return sessionMap.keySet().toArray(new String[0]);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Enumeration<String> getAttributeNames() {
        return new Vector(sessionMap.keySet()).elements();
    }

    public void putValue(String key, Object value) {
        sessionMap.put(key, value);
    }

    public void removeValue(String value) {}

    public long getCreationTime() {
        return 0;
    }

    public String getId() {
        return null;
    }

    public long getLastAccessedTime() {
        return 0;
    }

    public int getMaxInactiveInterval() {
        return 0;
    }

    public HttpSessionContext getSessionContext() {
        return null;
    }

    public void invalidate() {}

    public boolean isNew() {
        return false;
    }

    public void setMaxInactiveInterval(int arg0) {}
}