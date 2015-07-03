package cn.limw.summer.java.sql.wrapper;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年11月5日 下午1:22:32)
 * @since Java7
 */
public class DataSourceWrapper extends CommonDataSourceWrapper implements DataSource {
    private DataSource dataSource;

    public DataSourceWrapper() {}

    public DataSourceWrapper(DataSource dataSource) {
        setDataSource(dataSource);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getDataSource().unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getDataSource().isWrapperFor(iface);
    }

    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return getDataSource().getConnection(username, password);
    }

    public DataSource getDataSource() {
        return Asserts.noNull(dataSource);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        setCommonDataSource(dataSource);
    }
}