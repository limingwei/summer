package cn.limw.summer.task;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

import cn.limw.summer.spring.context.AbstractApplicationContextAware;
import cn.limw.summer.util.Logs;

/**
 * 同一内存空间的多个 ApplicationContext 任务只执行一次
 * @author li
 * @version 1 (2015年6月24日 下午5:52:53)
 * @since Java7
 */
public abstract class RunOneTimeInManyApplicationContextTask extends AbstractApplicationContextAware {
    private static final Logger log = Logs.slf4j();

    private static ApplicationContext _applicationContext = null;

    public synchronized final void doTask() {
        try {
            if (null == _applicationContext) {
                doTaskInternal();
                _applicationContext = getApplicationContext();
            } else if (_applicationContext.equals(getApplicationContext())) {
                doTaskInternal();
            } else {
                log.info("not running task in applicationContext {}", getApplicationContext());
            }
        } catch (Throwable e) {
            log.error("doTaskInternal error " + e, e);
        }
    }

    public abstract void doTaskInternal();
}