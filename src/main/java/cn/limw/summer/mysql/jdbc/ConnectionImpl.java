package cn.limw.summer.mysql.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cn.limw.summer.java.sql.NamedParameterPreparedStatement;
import cn.limw.summer.java.sql.wrapper.ConnectionWrapper;

/**
 * @author li
 * @version 1 (2014年11月7日 上午10:28:09)
 * @since Java7
 */
public class ConnectionImpl extends ConnectionWrapper {
    public ConnectionImpl(Connection connection) {
        super(connection);
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return new PreparedStatementImpl(new NamedParameterPreparedStatement(this, sql));
    }
}