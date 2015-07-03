package cn.limw.summer.shiro.session.dao.complex;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.session.mgt.eis.SessionDAO;

import cn.limw.summer.spring.beans.factory.SingletonFactoryBean;

/**
 * @author li
 * @version 1 (2014年11月20日 上午11:41:09)
 * @since Java7
 */
public class ComplexSessionDaoFactoryBean extends SingletonFactoryBean<SessionDAO> {
    private Map<Integer, SessionDAO> sessionDaos = new HashMap<Integer, SessionDAO>();

    public SessionDAO getObject() throws Exception {
        return new ComplexSessionDao(sessionDaos);
    }

    public Map<Integer, SessionDAO> getSessionDaos() {
        return sessionDaos;
    }

    public void setSessionDaos(Map<Integer, SessionDAO> sessionDaos) {
        this.sessionDaos = sessionDaos;
    }
}