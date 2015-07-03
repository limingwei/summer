package cn.limw.summer.mybatis.interceptor.logger;

import java.sql.Connection;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;

import cn.limw.summer.mybatis.interceptor.AbstractInterceptor;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年5月21日 上午10:33:59)
 * @since Java7
 * @see org.apache.ibatis.executor.Executor
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class })
})
public class StatementHandlerQueryLogger extends AbstractInterceptor {
    private static final Logger log = Logs.slf4j();

    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        log.info("sql={}, parameters={}", boundSql.getSql(), boundSql.getParameterObject());
        return super.intercept(invocation);
    }
}