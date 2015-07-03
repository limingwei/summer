package cn.limw.summer.async;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

/**
 * Handler
 * @author li
 * @version 1 (2014年7月5日 下午3:00:34)
 * @since Java7
 */
public abstract class AsyncRunner<T> {
    private static final Logger log = Logs.slf4j();

    private static final ThreadLocal<FutureTask<?>> FUTURE_TASK_THREAD_LOCAL = new ThreadLocal<FutureTask<?>>();

    private final Handler<T> NULL_HANDLER = new Handler<T>() {
        public void handle(Object target) {}
    };

    private Handler<T> handler;

    /**
     * 线程数
     */
    private Integer threads = 1;

    private ExecutorService executorService;

    public synchronized ExecutorService getExecutorService() {
        if (null == executorService) {
            executorService = Executors.newFixedThreadPool(getThreads());
            log.info("init executorService threads={} executorService={} this={}", getThreads(), executorService, this);
        }
        return executorService;
    }

    public FutureTask<?> getFutureTask() {
        return FUTURE_TASK_THREAD_LOCAL.get();
    }

    public FutureTask<T> doTask(Callable<T> callable) {
        FutureTask<T> future = new FutureTask<T>(callable);
        getExecutorService().execute(future);
        FUTURE_TASK_THREAD_LOCAL.set(future);
        return future;
    }

    public void doTask(final Runnable runnable) {
        FutureTask<T> future = new FutureTask<T>(new Callable<T>() {
            public T call() throws Exception {
                runnable.run();
                return null;
            }
        });
        getExecutorService().execute(future);
        FUTURE_TASK_THREAD_LOCAL.set(future);
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public Handler<T> getHandler() {
        return null != handler ? handler : NULL_HANDLER;
    }

    public void setHandler(Handler<T> handler) {
        this.handler = handler;
    }
}