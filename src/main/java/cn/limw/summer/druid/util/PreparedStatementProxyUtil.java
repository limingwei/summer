package cn.limw.summer.druid.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;
import com.alibaba.druid.sql.SQLUtils;

/**
 * @author li
 * @version 1 (2015年1月5日 下午4:53:14)
 * @since Java7
 */
public class PreparedStatementProxyUtil {
    public static String getExecutableSql(PreparedStatementProxy preparedStatementProxy) {
        if (null == preparedStatementProxy) {
            return " [PreparedStatementProxy is null] ";
        }

        String sql = preparedStatementProxy.getSql();
        int parametersSize = preparedStatementProxy.getParametersSize();

        List<Object> parameters = new ArrayList<Object>(parametersSize);
        for (int i = 0; i < parametersSize; ++i) {
            JdbcParameter jdbcParam = preparedStatementProxy.getParameter(i);
            parameters.add(jdbcParam.getValue());
        }

        String dbType = preparedStatementProxy.getConnectionProxy().getDirectDataSource().getDbType();
        return SQLUtils.format(sql, dbType, parameters);
    }
}