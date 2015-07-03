package cn.limw.summer.druid.filter;

import java.sql.SQLException;

import com.alibaba.druid.filter.FilterAdapter;
import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;

/**
 * @author li
 * @version 1 (2015年1月26日 下午4:31:36)
 * @since Java7
 */
public class ThreadLocalFilter extends FilterAdapter {
    private static final ThreadLocal<FilterChain> FILTER_CHAIN_THREAD_LOCAL = new ThreadLocal<FilterChain>();

    private static final ThreadLocal<PreparedStatementProxy> PREPARED_STATEMENT_PROXY_THREAD_LOCAL = new ThreadLocal<PreparedStatementProxy>();

    public static FilterChain getFilterChain() {
        return FILTER_CHAIN_THREAD_LOCAL.get();
    }

    public static PreparedStatementProxy getPreparedStatementProxy() {
        return PREPARED_STATEMENT_PROXY_THREAD_LOCAL.get();
    }

    public int preparedStatement_executeUpdate(FilterChain chain, PreparedStatementProxy statement) throws SQLException {
        FILTER_CHAIN_THREAD_LOCAL.set(chain);
        PREPARED_STATEMENT_PROXY_THREAD_LOCAL.set(statement);
        return super.preparedStatement_executeUpdate(chain, statement);
    }

    public ResultSetProxy preparedStatement_executeQuery(FilterChain chain, PreparedStatementProxy statement) throws SQLException {
        FILTER_CHAIN_THREAD_LOCAL.set(chain);
        PREPARED_STATEMENT_PROXY_THREAD_LOCAL.set(statement);
        return super.preparedStatement_executeQuery(chain, statement);
    }

    public boolean preparedStatement_execute(FilterChain chain, PreparedStatementProxy statement) throws SQLException {
        FILTER_CHAIN_THREAD_LOCAL.set(chain);
        PREPARED_STATEMENT_PROXY_THREAD_LOCAL.set(statement);
        return super.preparedStatement_execute(chain, statement);
    }
}