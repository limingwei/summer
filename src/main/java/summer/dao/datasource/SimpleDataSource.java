package summer.dao.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import javax.sql.DataSource;

import summer.log.Logger;
import summer.util.Log;

/**
 * @author li
 * @version 1 (2015年10月15日 下午1:25:38)
 * @since Java7
 */
public class SimpleDataSource implements DataSource {
    private static final String[] JDBC_DRIVERS = { "com.mysql.jdbc.Driver", "org.sqlite.JDBC", "org.h2.Driver", "org.apache.derby.jdbc.EmbeddedDriver", "org.hsqldb.jdbcDriver" };

    private static final Logger log = Log.slf4j();

    private String url;

    private String username;

    private String password;

    /**
     * 日志流
     */
    private PrintWriter logWriter;

    /**
     * 静态初始化代码块,注册数据库驱动
     */
    static {
        for (String driver : JDBC_DRIVERS) {
            registerDriver(driver);
        }
    }

    /**
     * 注册驱动
     */
    private static void registerDriver(String driver) {
        try {
            Class.forName(driver);
        } catch (Exception e) {}
    }

    /**
     * 注册JDBC驱动
     */
    public void setDriver(String driver) {
        registerDriver(driver);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 配置 user 或 username 都可以
     */
    public void setUser(String user) {
        this.username = user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("未实现");
    }

    /**
     * 不支持切换用户
     */
    public Connection getConnection(String username, String password) throws SQLException {
        log.warn("Not supported to use another username and password after dataSource inited");
        return this.getConnection();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.url, this.username, this.password);
    }

    public PrintWriter getLogWriter() throws SQLException {
        return this.logWriter;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        this.logWriter = out;
    }

    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        DriverManager.setLoginTimeout(seconds);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return null != iface && iface.isInstance(this);
    }

    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null != iface && iface.isInstance(this) ? (T) this : null;
    }

    public String toString() {
        return super.toString() + " url=" + this.url;
    }
}