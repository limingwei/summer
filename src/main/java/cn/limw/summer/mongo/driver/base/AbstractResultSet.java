package cn.limw.summer.mongo.driver.base;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cn.limw.summer.java.sql.wrapper.ResultSetWrapper;
import cn.limw.summer.mongo.driver.util.Logs;

/**
 * @author li
 * @version 1 2014年3月12日上午9:27:57
 */
public class AbstractResultSet extends ResultSetWrapper {
    private static final Logger log = Logs.get();

    public void close() throws SQLException {
        log.trace("close " + this);
    }

    public int getType() throws SQLException {
        return ResultSet.TYPE_FORWARD_ONLY;
    }

    public boolean wasNull() throws SQLException {
        return false;
    }

    public Object getObject(int columnIndex) throws SQLException {
        return getObject(getColumnLabel(columnIndex));
    }

    public String getString(String columnLabel) throws SQLException {
        Object object = getObject(columnLabel);
        return null == object ? null : object.toString();
    }

    public String getString(int columnIndex) throws SQLException {
        return getString(getColumnLabel(columnIndex));
    }

    public int getInt(String columnLabel) throws SQLException {
        String string = getString(columnLabel);
        return null == string ? 0 : new Integer(string);
    }

    public int getInt(int columnIndex) throws SQLException {
        return getInt(getColumnLabel(columnIndex));
    }

    public double getDouble(int columnIndex) throws SQLException {
        return getDouble(getColumnLabel(columnIndex));
    }

    public double getDouble(String columnLabel) throws SQLException {
        return new Double(getString(columnLabel));
    }

    public long getLong(int columnIndex) throws SQLException {
        return getLong(getColumnLabel(columnIndex));
    }

    public long getLong(String columnLabel) throws SQLException {
        return new Long(getString(columnLabel));
    }

    public short getShort(int columnIndex) throws SQLException {
        return getShort(getColumnLabel(columnIndex));
    }

    public short getShort(String columnLabel) throws SQLException {
        return new Short(getString(columnLabel));
    }

    public float getFloat(int columnIndex) throws SQLException {
        return getFloat(getColumnLabel(columnIndex));
    }

    public float getFloat(String columnLabel) throws SQLException {
        return new Float(getString(columnLabel));
    }

    private String getColumnLabel(int columnIndex) throws SQLException {
        return getMetaData().getColumnLabel(columnIndex);
    }
}