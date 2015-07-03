package cn.limw.summer.junit;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

/**
 * 什么也不做的JunitTestRunner
 * @author li
 * @version 1 (2014年6月19日 上午11:49:59)
 * @since Java7
 */
public class NullRunner extends Runner {
    public NullRunner(Class<?> clazz) {}

    public Description getDescription() {
        return null;
    }

    public void run(RunNotifier notifier) {}
}