package cn.limw.summer.java.sql.wrapper;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.CommonDataSource;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年11月5日 下午1:22:53)
 * @since Java7
 */
public class CommonDataSourceWrapper implements CommonDataSource {
    private CommonDataSource commonDataSource;

    public CommonDataSourceWrapper() {}

    public CommonDataSourceWrapper(CommonDataSource commonDataSource) {
        setCommonDataSource(commonDataSource);
    }

    public PrintWriter getLogWriter() throws SQLException {
        return getCommonDataSource().getLogWriter();
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        getCommonDataSource().setLogWriter(out);
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        getCommonDataSource().setLoginTimeout(seconds);
    }

    public int getLoginTimeout() throws SQLException {
        return getCommonDataSource().getLoginTimeout();
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return getCommonDataSource().getParentLogger();
    }

    public CommonDataSource getCommonDataSource() {
        return Asserts.noNull(commonDataSource);
    }

    public void setCommonDataSource(CommonDataSource commonDataSource) {
        this.commonDataSource = commonDataSource;
    }
}