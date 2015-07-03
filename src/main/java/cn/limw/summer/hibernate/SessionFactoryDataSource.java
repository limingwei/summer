package cn.limw.summer.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.SessionFactory;

import cn.limw.summer.java.sql.wrapper.DataSourceWrapper;

/**
 * @author li
 * @version 1 (2014年11月5日 下午1:33:24)
 * @since Java7
 */
public class SessionFactoryDataSource extends DataSourceWrapper {
    private SessionFactory sessionFactory;

    public SessionFactoryDataSource(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Connection getConnection() throws SQLException {
        return new SessionConnection(HibernateUtil.getCurrentSession(getSessionFactory()));
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}