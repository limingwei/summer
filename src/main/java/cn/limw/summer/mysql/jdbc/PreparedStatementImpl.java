package cn.limw.summer.mysql.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.limw.summer.java.sql.wrapper.PreparedStatementWrapper;

/**
 * @author li
 * @version 1 (2014年11月7日 上午10:28:30)
 * @since Java7
 */
public class PreparedStatementImpl extends PreparedStatementWrapper {
    public PreparedStatementImpl(PreparedStatement prepareStatement) {
        super(prepareStatement);
    }

    public ResultSet executeQuery() throws SQLException {
        return new ResultSetImpl(super.executeQuery());
    }

    /**
     * 这里可以做敏感词检查
     */
    public void setString(int parameterIndex, String x) throws SQLException {
        super.setString(parameterIndex, x);
    }
}