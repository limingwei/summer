package cn.limw.summer.druid.filter;

import java.sql.SQLException;

import cn.limw.summer.druid.util.PreparedStatementProxyUtil;

import com.alibaba.druid.filter.FilterAdapter;
import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;

/**
 * @author li
 * @version 1 (2015年1月5日 下午4:43:08)
 * @since Java7
 */
public class PreparedStatementExecuteQueryErrorFilter extends FilterAdapter {
    public ResultSetProxy preparedStatement_executeQuery(FilterChain chain, PreparedStatementProxy preparedStatementProxy) throws SQLException {
        try {
            return super.preparedStatement_executeQuery(chain, preparedStatementProxy);
        } catch (SQLException e) {
            throw new SQLException("sql=" + PreparedStatementProxyUtil.getExecutableSql(preparedStatementProxy), e);
        }
    }
}