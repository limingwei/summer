package cn.limw.summer.mysql.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.limw.summer.java.sql.wrapper.ResultSetWrapper;

/**
 * @author li
 * @version 1 (2014年11月7日 上午10:28:49)
 * @since Java7
 */
public class ResultSetImpl extends ResultSetWrapper {
    public ResultSetImpl(ResultSet resultSet) {
        super(resultSet);
    }

    public Object getObject(int columnIndex) throws SQLException {
        return super.getObject(columnIndex);
    }

    public String getString(String columnLabel) throws SQLException {
        return super.getString(columnLabel);
    }
}