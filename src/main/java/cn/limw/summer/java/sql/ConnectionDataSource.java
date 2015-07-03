package cn.limw.summer.java.sql;

import java.sql.Connection;
import java.sql.SQLException;

import cn.limw.summer.java.sql.wrapper.DataSourceWrapper;

/**
 * @author li
 * @version 1 (2014年11月17日 下午2:55:46)
 * @since Java7
 */
public class ConnectionDataSource extends DataSourceWrapper {
    private Connection connection;

    public ConnectionDataSource(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() throws SQLException {
        return this.connection;
    }
}