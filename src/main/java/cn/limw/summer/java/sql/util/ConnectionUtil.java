package cn.limw.summer.java.sql.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author li
 * @version 1 (2014年11月20日 下午3:47:23)
 * @since Java7
 */
public class ConnectionUtil {
    public static Connection get(String url, String user, String password) {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}