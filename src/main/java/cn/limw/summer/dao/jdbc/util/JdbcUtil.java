package cn.limw.summer.dao.jdbc.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 * @author li
 * @version 1 (2015年5月18日 下午2:55:22)
 * @since Java7
 */
public class JdbcUtil {
    public static Integer executeUpdate(DataSource dataSource, String sql) {
        Connection connection = getConnection(dataSource);
        Statement statement = createStatement(connection);
        Integer rows = executeUpdate(statement, sql);
        close(statement);
        close(connection);
        return rows;
    }

    public static void close(AutoCloseable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer executeUpdate(Statement statement, String sql) {
        try {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Statement createStatement(Connection connection) {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(DataSource dataSource) {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}