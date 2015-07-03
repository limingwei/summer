package cn.limw.summer.mybatis.interceptor.logger;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;

import cn.limw.summer.mybatis.interceptor.AbstractInterceptor;
import cn.limw.summer.util.ArrayUtil;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年5月21日 下午6:14:00)
 * @since Java7
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })
})
public class ExecutorQueryPerformanceInterceptor extends AbstractInterceptor {
    private static final Logger log = Logs.slf4j();

    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = ArrayUtil.select(invocation.getArgs(), 1);
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);

        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        log.info("sql={}, parameters={}, 耗时: {} ms", boundSql.getSql(), boundSql.getParameterObject(), (System.currentTimeMillis() - start));
        return result;
    }
}