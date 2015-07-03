package cn.limw.summer.log4j.appender.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.jdbc.JDBCAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author li
 * @version 1 (2015年6月30日 下午3:40:38)
 * @since Java7
 */
public class JdbcAppender extends JDBCAppender {
    protected void execute(String sql) throws SQLException {
        Connection con = null;
        Statement stmt = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (Throwable e) {
            new RuntimeException("sql=" + sql, e).printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection(con);
        }
    }

    protected String getLogStatement(LoggingEvent event) {
        return getLayout().format(event);
    }
}