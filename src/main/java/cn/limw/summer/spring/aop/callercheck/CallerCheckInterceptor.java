package cn.limw.summer.spring.aop.callercheck;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;

import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年2月6日 下午1:13:21)
 * @since Java7
 */
public class CallerCheckInterceptor implements MethodInterceptor {
    private static final Logger log = Logs.slf4j();

    public Object invoke(MethodInvocation invocation) throws Throwable {
        // CallerCheck callerCheck = invocation.getMethod().getAnnotation(CallerCheck.class);

        Class<? extends Object> targetType = invocation.getThis().getClass();
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement caller = null;
        for (int i = 0; i < stackTraceElements.length; i++) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
            if (stackTraceElement.getClassName().startsWith(targetType.getName() + "$$EnhancerBySpringCGLIB$$")) {
                caller = stackTraceElements[i + 1];
                break;
            }
        }
        Asserts.noNull(caller, "found caller null");

        log.info("method={}, caller={}, callerMethod={}", invocation.getMethod(), caller);

        return invocation.proceed();
    }
}