package cn.limw.summer.shiro.session.dao.wrapper;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.SessionDAO;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年11月20日 下午1:34:31)
 * @since Java7
 */
public class SessionDaoWrapper implements SessionDAO {
    private SessionDAO sessionDao;

    public SessionDAO getSessionDao() {
        return Asserts.noNull(sessionDao);
    }

    public void setSessionDao(SessionDAO sessionDao) {
        this.sessionDao = sessionDao;
    }

    public SessionDaoWrapper() {}

    public SessionDaoWrapper(SessionDAO sessionDAO) {
        this.sessionDao = sessionDAO;
    }

    public Serializable create(Session session) {
        return getSessionDao().create(session);
    }

    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        return getSessionDao().readSession(sessionId);
    }

    public void update(Session session) throws UnknownSessionException {
        getSessionDao().update(session);
    }

    public void delete(Session session) {
        getSessionDao().delete(session);
    }

    public Collection<Session> getActiveSessions() {
        return getSessionDao().getActiveSessions();
    }
}