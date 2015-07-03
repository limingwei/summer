package cn.limw.summer.javax.servlet.http.wrapper;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2015年2月25日 下午4:54:21)
 * @since Java7
 */
public class HttpSessionWrapper implements HttpSession {
    private HttpSession httpSession;

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public HttpSession getHttpSessionNoNull() {
        return Asserts.noNull(getHttpSession());
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public HttpSessionWrapper(HttpSession httpSession) {
        setHttpSession(httpSession);
    }

    public long getCreationTime() {
        return getHttpSession().getCreationTime();
    }

    public String getId() {
        return getHttpSession().getId();
    }

    public long getLastAccessedTime() {
        return getHttpSession().getLastAccessedTime();
    }

    public ServletContext getServletContext() {
        return getHttpSession().getServletContext();
    }

    public void setMaxInactiveInterval(int interval) {
        getHttpSession().setMaxInactiveInterval(interval);
    }

    public int getMaxInactiveInterval() {
        return getHttpSession().getMaxInactiveInterval();
    }

    public HttpSessionContext getSessionContext() {
        return getHttpSession().getSessionContext();
    }

    public Object getAttribute(String name) {
        return getHttpSession().getAttribute(name);
    }

    public Object getValue(String name) {
        return getHttpSession().getValue(name);
    }

    public Enumeration<String> getAttributeNames() {
        return getHttpSession().getAttributeNames();
    }

    public String[] getValueNames() {
        return getHttpSession().getValueNames();
    }

    public void setAttribute(String name, Object value) {
        getHttpSession().setAttribute(name, value);
    }

    public void putValue(String name, Object value) {
        getHttpSession().putValue(name, value);
    }

    public void removeAttribute(String name) {
        getHttpSession().removeAttribute(name);
    }

    public void removeValue(String name) {
        getHttpSession().removeValue(name);
    }

    public void invalidate() {
        getHttpSession().invalidate();
    }

    public boolean isNew() {
        return getHttpSession().isNew();
    }

    public String toString() {
        return super.toString() + ", type=" + getClass().getName() + ", hashCode=" + hashCode() + ", id=" + getId() // 
                + ", innerSession=" + getHttpSessionNoNull() + ", innerSession.type=" + getHttpSessionNoNull().getClass().getName() //
                + ", innerSession.hashCode=" + getHttpSessionNoNull().hashCode() + ", innerSession.id=" + getHttpSessionNoNull().getId();
    }
}