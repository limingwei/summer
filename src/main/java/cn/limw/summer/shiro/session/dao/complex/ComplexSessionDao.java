package cn.limw.summer.shiro.session.dao.complex;

import java.io.Serializable;
import java.util.Map;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.SessionDAO;

import cn.limw.summer.shiro.session.dao.wrapper.SessionDaoWrapper;

/**
 * @author li
 * @version 1 (2014年11月20日 下午1:39:22)
 * @since Java7
 */
public class ComplexSessionDao extends SessionDaoWrapper {
    private Map<Integer, SessionDAO> sessionDaos;

    private Integer index = 0;

    /**
     * 返回SessionDAO引用
     */
    public SessionDAO getSessionDao() {
        return sessionDaos.get(index);
    }

    public ComplexSessionDao(Map<Integer, SessionDAO> sessionDaos) {
        this.sessionDaos = sessionDaos;
    }

    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        try {
            return super.readSession(sessionId);
        } catch (Exception e) {
            if (index > sessionDaos.size()) {
                throw new RuntimeException("没有找到可用的 sessionDao ", e);
            } else {
                index++;
            }
            return readSession(sessionId);
        }
    }
}