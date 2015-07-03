package cn.limw.summer.spring.scheduling.config;

import org.slf4j.Logger;
import org.springframework.core.task.TaskExecutor;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年4月20日 上午9:11:57)
 * @since Java7
 */
public class TaskExecutorFactoryBean extends AbstractTaskExecutorFactoryBean {
    private static final Logger log = Logs.slf4j();

    private Boolean singleInSameMemory = true;

    private static TaskExecutor taskExecutor = null;

    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }

    /**
     * 一个内存空间里单例
     */
    public TaskExecutor getObject() {
        TaskExecutor instense = null;

        if (getSingleInSameMemory()) {
            if (null != taskExecutor) {
                instense = taskExecutor;
            } else {
                instense = doGetObject();
            }
        } else {
            instense = super.getObject();
        }

        log.info("getObject() this={}, instense={}", this, instense);
        return instense;
    }

    private synchronized final TaskExecutor doGetObject() {
        if (null != taskExecutor) {
            return taskExecutor;
        } else {
            return taskExecutor = super.getObject();
        }
    }

    public Boolean getSingleInSameMemory() {
        return singleInSameMemory;
    }

    public void setSingleInSameMemory(Boolean singleInSameMemory) {
        this.singleInSameMemory = singleInSameMemory;
    }
}