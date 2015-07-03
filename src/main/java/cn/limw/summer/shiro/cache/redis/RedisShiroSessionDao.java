package cn.limw.summer.shiro.cache.redis;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.springframework.util.SerializationUtils;

import cn.limw.summer.shiro.cache.ICached;
import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.util.Logs;

/**
 * @author lgb
 * @version 1 (2014年9月10日上午10:18:12)
 * @since Java7
 */
public class RedisShiroSessionDao extends AbstractSessionDAO {
    private static final Logger log = Logs.slf4j();

    private String sessionIdPrefix = "shiro-session-redis-";

    private ICached cached;

    public ICached getCached() {
        return cached;
    }

    public void setCached(ICached cached) {
        this.cached = cached;
    }

    public String getSessionIdPrefix() {
        return sessionIdPrefix;
    }

    public void setSessionIdPrefix(String sessionIdPrefix) {
        this.sessionIdPrefix = sessionIdPrefix;
    }

    public void update(Session session) throws UnknownSessionException {
        try {
            cached.updateCached(session.getId().toString().getBytes(), SerializationUtils.serialize(session), session.getTimeout() / 1000);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void delete(Session session) {
        try {
            cached.deleteCached(session.getId().toString().getBytes());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public Collection<Session> getActiveSessions() {
        String keys = getSessionIdPrefix() + "*";
        Set<Session> set = null;
        try {
            set = cached.getKeys(keys.getBytes());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return set;
    }

    protected Serializable doCreate(Session session) {
        Serializable sessionId = session.getId();
        try {
            super.assignSessionId(session, getSessionIdPrefix() + super.generateSessionId(session));
            update(session);
            sessionId = session.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return sessionId;
    }

    /**
     * UnknownSessionException
     */
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        try {
            if (isSessionIdIllegal(sessionId)) { // 非法的SessionId
                if (null == Mvcs.getRequest()) {
                    log.error("readSession() illegal sessionid " + sessionId + ", ip=" + Mvcs.getRemoteAddr() //
                            + ", Accept=" + Mvcs.getRequestHeader("Accept") //
                            + ", Accept-Encoding=" + Mvcs.getRequestHeader("Accept-Encoding") //
                            + ", Accept-Language=" + Mvcs.getRequestHeader("Accept-Language") //
                            + ", User-Agent=" + Mvcs.getRequestHeader("User-Agent") //
                            + ", Referer=" + Mvcs.getRequestHeader("Referer"));
                } else {
                    log.error("readSession() illegal sessionid " + sessionId);
                }
            }

            return super.readSession(sessionId);
        } catch (UnknownSessionException e) {
            if (null == Mvcs.getRequest()) {
                log.error("UnknownSessionException, sessionid=" + sessionId + ", ip=" + Mvcs.getRemoteAddr() //
                        + ", Accept=" + Mvcs.getRequestHeader("Accept") //
                        + ", Accept-Encoding=" + Mvcs.getRequestHeader("Accept-Encoding") //
                        + ", Accept-Language=" + Mvcs.getRequestHeader("Accept-Language") //
                        + ", User-Agent=" + Mvcs.getRequestHeader("User-Agent") //
                        + ", Referer=" + Mvcs.getRequestHeader("Referer"));
            } else {
                log.error("UnknownSessionException, sessionid=" + sessionId);
            }

            throw e;
        }
    }

    /**
     * SessionId 非法
     */
    private Boolean isSessionIdIllegal(Serializable sessionId) {
        if (null == sessionId) {
            return true;
        } else {
            String sessionIdString = sessionId.toString();
            if (!sessionIdString.startsWith(getSessionIdPrefix())) {
                return true;
            } else if (sessionIdString.contains("/") || sessionIdString.contains("\\")) {
                return true;
            }
        }
        return false;
    }

    protected Session doReadSession(Serializable sessionId) {
        Session session = null;
        try {
            session = (Session) cached.getCached(sessionId.toString().getBytes());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return session;
    }
}