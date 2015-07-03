package cn.limw.summer.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;

import cn.limw.summer.java.sql.wrapper.ConnectionWrapper;

/**
 * @author li
 * @version 1 (2014年11月5日 下午1:41:36)
 * @since Java7
 */
public class SessionConnection extends ConnectionWrapper {
    private Session session;

    public SessionConnection(Session session) {
        this.session = session;
    }

    public boolean getAutoCommit() throws SQLException {
        return getSession().doReturningWork(new ReturningWork<Boolean>() {
            public Boolean execute(Connection connection) throws SQLException {
                return connection.getAutoCommit();
            }
        });
    }

    public PreparedStatement prepareStatement(final String sql) throws SQLException {
        return getSession().doReturningWork(new ReturningWork<PreparedStatement>() {
            public PreparedStatement execute(Connection connection) throws SQLException {
                return connection.prepareStatement(sql);
            }
        });
    }

    public Session getSession() {
        return session;
    }
}