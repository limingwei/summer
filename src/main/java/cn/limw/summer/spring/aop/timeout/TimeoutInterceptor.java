package cn.limw.summer.spring.aop.timeout;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年3月18日 下午6:55:12)
 * @since Java7
 */
public class TimeoutInterceptor implements MethodInterceptor {
    private static final Logger log = Logs.slf4j();

    private Integer defaultTimeout = 5 * 60;

    private Boolean throwTimeoutException = false;

    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Timeout timeout = invocation.getMethod().getAnnotation(Timeout.class);
        if (null == timeout) {
            return invocation.proceed();
        } else {
            return doTimeoutInvoke(invocation);
        }
    }

    private Object doTimeoutInvoke(final MethodInvocation invocation) throws TimeoutException {
        log.info("TimeoutInterceptor.doTimeoutInvoke invocation=" + invocation);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        FutureTask<Object> futureTask = new FutureTask<Object>(new Callable<Object>() {
            public Object call() throws Exception {
                return proceedInvocation(invocation);
            }
        });

        executorService.submit(futureTask);

        Object result = null;
        try {
            result = futureTask.get(getTimeout(invocation), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("TimeoutInterceptor.doInvoke catch InterruptedException", e);
        } catch (ExecutionException e) {
            log.error("TimeoutInterceptor.doInvoke catch ExecutionException", e);
        } catch (TimeoutException e) {
            e.getMessage();
            if (getThrowTimeoutException()) {
                throw e;
            } else {
                log.error("TimeoutInterceptor.doInvoke catch TimeoutException", e);
            }
        }

        return result;
    }

    protected Object proceedInvocation(MethodInvocation invocation) {
        try {
            return invocation.proceed();
        } catch (Throwable e) {
            log.error("invocation.proceed error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 单位秒
     */
    private Integer getTimeout(MethodInvocation invocation) {
        Timeout timeout = invocation.getMethod().getAnnotation(Timeout.class);
        int seconds = (null == timeout || timeout.value() < 0) ? getDefaultTimeout() : timeout.value();
        return seconds;
    }

    public Integer getDefaultTimeout() {
        return defaultTimeout;
    }

    public void setDefaultTimeout(Integer defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    public Boolean getThrowTimeoutException() {
        return throwTimeoutException;
    }

    public void setThrowTimeoutException(Boolean throwTimeoutException) {
        this.throwTimeoutException = throwTimeoutException;
    }
}