package cn.limw.summer.shiro.session.wrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2015年5月27日 下午4:33:45)
 * @since Java7
 */
public class SessionWrapper implements Session, Serializable {
    private static final long serialVersionUID = -4228855838758425645L;

    private Session session;

    public Session getSession() {
        return Asserts.noNull(session);
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public SessionWrapper() {}

    public SessionWrapper(Session session) {
        setSession(session);
    }

    public Serializable getId() {
        return getSession().getId();
    }

    public Date getStartTimestamp() {
        return getSession().getStartTimestamp();
    }

    public Date getLastAccessTime() {
        return getSession().getLastAccessTime();
    }

    public long getTimeout() throws InvalidSessionException {
        return getSession().getTimeout();
    }

    public void setTimeout(long maxIdleTimeInMillis) throws InvalidSessionException {
        getSession().setTimeout(maxIdleTimeInMillis);
    }

    public String getHost() {
        return getSession().getHost();
    }

    public void touch() throws InvalidSessionException {
        getSession().touch();
    }

    public void stop() throws InvalidSessionException {
        getSession().stop();
    }

    public Collection<Object> getAttributeKeys() throws InvalidSessionException {
        return getSession().getAttributeKeys();
    }

    public Object getAttribute(Object key) throws InvalidSessionException {
        return getSession().getAttribute(key);
    }

    public void setAttribute(Object key, Object value) throws InvalidSessionException {
        getSession().setAttribute(key, value);
    }

    public Object removeAttribute(Object key) throws InvalidSessionException {
        return getSession().removeAttribute(key);
    }
}