package cn.limw.summer.spring.scheduling.concurrent;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.scheduling.TaskScheduler;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年4月20日 上午9:45:05)
 * @since Java7
 */
public class ThreadPoolTaskScheduler extends AbstractThreadPoolTaskScheduler //
        implements AsyncListenableTaskExecutor, SchedulingTaskExecutor, TaskScheduler, BeanNameAware, InitializingBean, DisposableBean, ThreadFactory, Serializable {
    private static final long serialVersionUID = -7431569489145199463L;

    private static final Logger log = Logs.slf4j();

    private Boolean singleInSameMemory = true;

    private ExecutorService executorService = null;

    public void afterPropertiesSet() {
        super.afterPropertiesSet();
    }

    protected ExecutorService initializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        ExecutorService instense = null;
        if (getSingleInSameMemory()) {
            if (null != executorService) {
                instense = executorService;
            } else {
                instense = executorService = doInitializeExecutor(threadFactory, rejectedExecutionHandler);
            }
        } else {
            executorService = super.initializeExecutor(threadFactory, rejectedExecutionHandler);
        }

        log.info("initializeExecutor() this={}, executorService={}", this, instense);
        return instense;
    }

    private synchronized final ExecutorService doInitializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        if (null != executorService) {
            return executorService;
        } else {
            return super.initializeExecutor(threadFactory, rejectedExecutionHandler);
        }
    }

    public Boolean getSingleInSameMemory() {
        return singleInSameMemory;
    }

    public void setSingleInSameMemory(Boolean singleInSameMemory) {
        this.singleInSameMemory = singleInSameMemory;
    }
}