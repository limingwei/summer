package cn.limw.summer.spring.aop.timelog;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年3月19日 下午4:46:11)
 * @since Java7
 */
public class TimelogInterceptor implements MethodInterceptor {
    private static final Logger log = Logs.slf4j();

    private Integer defaultMinTime = 0;

    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Timelog timer = invocation.getMethod().getAnnotation(Timelog.class);
        if (null == timer) {
            return invocation.proceed();
        } else {
            return doInvoke(invocation);
        }
    }

    private Object doInvoke(MethodInvocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        long timeCost = System.currentTimeMillis() - start;
        if (timeCost >= getMinTime(invocation)) {
            log.info("method " + invocation.getMethod() + " cost " + timeCost + " ms");
        }
        return result;
    }

    private Integer getMinTime(MethodInvocation invocation) {
        Timelog timer = invocation.getMethod().getAnnotation(Timelog.class);
        int seconds = (null == timer || timer.min() < 0) ? getDefaultMinTime() : timer.min();
        return seconds;
    }

    public Integer getDefaultMinTime() {
        return defaultMinTime;
    }

    public void setDefaultMinTime(Integer defaultMinTime) {
        this.defaultMinTime = defaultMinTime;
    }
}