package cn.limw.summer.junit;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2014年6月19日 上午11:33:57)
 * @since Java7
 */
public class ConditionalSkipRunner extends Runner {
    private Runner runner;

    public ConditionalSkipRunner(Class<?> clazz) throws InitializationError {
        Boolean skip = skipTest();
        if (skip) {
            this.runner = new NullRunner(clazz);
        } else {
            cn.limw.summer.junit.Runner _runner = clazz.getAnnotation(cn.limw.summer.junit.Runner.class);
            if (null != _runner) {
                this.runner = Mirrors.newInstance(_runner.value(), clazz);
            } else {
                this.runner = new BlockJUnit4ClassRunner(clazz);
            }
        }
    }

    /**
     * -D
     * properties
     */
    public boolean skipTest() {
        return false;
    }

    public Description getDescription() {
        return runner.getDescription();
    }

    public void run(RunNotifier notifier) {
        runner.run(notifier);
    }
}