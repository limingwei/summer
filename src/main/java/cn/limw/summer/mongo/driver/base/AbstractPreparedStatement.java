package cn.limw.summer.mongo.driver.base;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cn.limw.summer.java.sql.wrapper.PreparedStatementWrapper;
import cn.limw.summer.mongo.driver.util.Logs;

/**
 * @author li
 * @version 1 2014年3月12日上午9:27:54
 */
public class AbstractPreparedStatement extends PreparedStatementWrapper {
    private static final Logger log = Logs.get();

    public void setFetchSize(int rows) throws SQLException {};

    public void setQueryTimeout(int seconds) throws SQLException {}

    public void setMaxRows(int max) throws SQLException {}

    public int getMaxFieldSize() throws SQLException {
        return Integer.MAX_VALUE;
    }

    public int getFetchDirection() throws SQLException {
        return ResultSet.FETCH_FORWARD;
    }

    public int getFetchSize() throws SQLException {
        return Integer.MAX_VALUE;
    }

    public int getMaxRows() throws SQLException {
        return Integer.MAX_VALUE;
    }

    public int getQueryTimeout() throws SQLException {
        return Integer.MAX_VALUE;
    }

    public void close() throws SQLException {
        log.trace("close " + this);
    }

    public boolean getMoreResults() throws SQLException {
        return false;
    }

    public void setInt(int index, int value) throws SQLException {
        setObject(index, value);
    }

    public void setLong(int index, long value) throws SQLException {
        setObject(index, value);
    }

    public void setString(int index, String value) throws SQLException {
        setObject(index, value);
    }
}