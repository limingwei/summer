package cn.limw.summer.java.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import cn.limw.summer.util.Errors;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年4月15日 上午10:51:01)
 * @since Java7
 */
public class SimpleDataSource implements DataSource {
    private static final org.slf4j.Logger log = Logs.slf4j();

    private String username;

    private String password;

    protected String driverClassName;

    private String jdbcUrl;

    public SimpleDataSource() {
        log.warn("SimpleDataSource is use for Test/Attempt, NOT Using in Production environment!");
        log.warn("SimpleDataSource is NOT a Connection Pool, So it is slow but safe for debug/study");
    }

    public SimpleDataSource(String jdbcUrl, String username, String password) {
        setJdbcUrl(jdbcUrl);
        setUsername(username);
        setPassword(password);
    }

    public Connection getConnection() throws SQLException {
        Connection conn;
        if (username != null)
            conn = DriverManager.getConnection(jdbcUrl, username, password);
        else
            conn = DriverManager.getConnection(jdbcUrl);
        return conn;
    }

    public void close() {}

    public void setDriverClassName(String driverClassName) throws ClassNotFoundException {
        Class.forName(driverClassName);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setUrl(String jdbcUrl) {
        setJdbcUrl(jdbcUrl);
    }

    //加载Nutz所支持的数据库的驱动!!
    static {
        String[] drivers = { "org.h2.Driver",
                "com.ibm.db2.jcc.DB2Driver",
                "org.hsqldb.jdbcDriver",
                "org.gjt.mm.mysql.Driver",
                "oracle.jdbc.OracleDriver",
                "org.postgresql.Driver",
                "net.sourceforge.jtds.jdbc.Driver",
                "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                "org.sqlite.JDBC" };
        for (String driverClassName : drivers) {
            try {
                Class.forName(driverClassName);
            } catch (Throwable e) {}
        }
    }

    //---------------------------------------------------------------

    public PrintWriter getLogWriter() throws SQLException {
        throw Errors.notImplemented();
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        throw Errors.notImplemented();
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        throw Errors.notImplemented();
    }

    public int getLoginTimeout() throws SQLException {
        throw Errors.notImplemented();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw Errors.notImplemented();
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw Errors.notImplemented();
    }

    public Connection getConnection(String username, String password) throws SQLException {
        throw Errors.notImplemented();
    }

    public Logger getParentLogger() {
        throw Errors.notImplemented();
    }
}