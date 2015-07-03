package cn.limw.summer.mybatis.interceptor;

import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

/**
 * 支持 对 Executor、StatementHandler、PameterHandler和ResultSetHandler 拦截
 * @author li
 * @version 1 (2014年11月18日 下午1:54:57)
 * @since Java7
 */
public class AbstractInterceptor implements Interceptor {
    public void setProperties(Properties properties) {}

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }
}