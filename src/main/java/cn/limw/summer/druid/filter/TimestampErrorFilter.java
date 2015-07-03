package cn.limw.summer.druid.filter;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import org.slf4j.Logger;

import cn.limw.summer.time.Clock;
import cn.limw.summer.time.DateFormatPool;
import cn.limw.summer.util.Logs;

import com.alibaba.druid.filter.FilterAdapter;
import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;

/**
 * @author li
 * @version 1 (2014年11月3日 上午9:27:39)
 * @since Java7
 */
public class TimestampErrorFilter extends FilterAdapter {
    private static final Logger log = Logs.slf4j();

    public static final String DEFAULT_VALUE_NULL = "null";

    public static final String DEFAULT_VALUE_NOW = "now";

    private String defaultValue = DEFAULT_VALUE_NULL;

    public java.sql.Timestamp resultSet_getTimestamp(FilterChain chain, ResultSetProxy result, String columnLabel) throws SQLException {
        try {
            return chain.resultSet_getTimestamp(result, columnLabel);
        } catch (Exception e1) {
            try {
                String value = chain.resultSet_getString(result, columnLabel);
                return Clock.when(value, DateFormatPool.YYYY_MM_DD_HH_MM_SS).toTimestamp();
            } catch (Exception e2) {
                log.error("resultSet_getTimestamp error e1 " + e1, e1);
                log.error("resultSet_getTimestamp error e2 " + e2, e2);
                return defaultValue();
            }
        }
    }

    public void preparedStatement_setTimestamp(FilterChain chain, PreparedStatementProxy statement, int parameterIndex, Timestamp x) throws SQLException {
        super.preparedStatement_setTimestamp(chain, statement, parameterIndex, x);
    }

    public void preparedStatement_setTimestamp(FilterChain chain, PreparedStatementProxy statement, int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        super.preparedStatement_setTimestamp(chain, statement, parameterIndex, x, cal);
    }

    protected Timestamp defaultValue() {
        if (DEFAULT_VALUE_NULL.equalsIgnoreCase(getDefaultValue())) {
            return null;
        } else if (DEFAULT_VALUE_NOW.equalsIgnoreCase(getDefaultValue())) {
            return Clock.nowTimestamp();
        } else {
            return Clock.when(getDefaultValue(), DateFormatPool.YYYY_MM_DD_HH_MM_SS).toTimestamp();
        }
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}