package cn.limw.summer.mongo.driver.meta.base;

import java.sql.SQLException;
import java.sql.Types;

import cn.limw.summer.java.sql.wrapper.ResultSetMetaDataWrapper;

/**
 * @author li
 * @version 1 2014年3月12日上午11:07:13
 */
public class AbstractResultSetMetaData extends ResultSetMetaDataWrapper {
    public int getColumnType(int column) throws SQLException {
        return Types.VARCHAR;
    }

    public String getColumnClassName(int column) throws SQLException {
        return String.class.getName();
    }
}