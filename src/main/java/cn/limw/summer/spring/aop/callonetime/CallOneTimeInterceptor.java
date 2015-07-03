package cn.limw.summer.spring.aop.callonetime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

import cn.limw.summer.spring.context.AbstractApplicationContextAware;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年4月23日 上午9:07:05)
 * @since Java7
 */
public class CallOneTimeInterceptor extends AbstractApplicationContextAware implements MethodInterceptor {
    private static final Logger log = Logs.slf4j();

    /**
     * 方法在这个ApplicationContext会被真正Call, 其他ApplicationContext则略过
     */
    private static final Map<String, ApplicationContext> METHOD_TO_CALL_CONTEXT_MAP = new ConcurrentHashMap<String, ApplicationContext>();

    /**
     * synchronized 方法
     */
    public synchronized Object invoke(MethodInvocation invocation) throws Throwable {
        String methodString = invocation.getMethod().toGenericString();
        ApplicationContext applicationContext = METHOD_TO_CALL_CONTEXT_MAP.get(methodString);
        if (null == applicationContext) { // 初次
            METHOD_TO_CALL_CONTEXT_MAP.put(methodString, getApplicationContext());
            return invocation.proceed();
        } else if (applicationContext.equals(getApplicationContext())) { // 同一ApplicationContext后次
            return invocation.proceed();
        } else { // 其他 ApplicationContext
            log.debug("applicationContext={} not calling method {}", getApplicationContext(), invocation.getMethod());
            return null;
        }
    }
}