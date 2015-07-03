package cn.limw.summer.spring.test.context.junit4;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2014年10月28日 上午9:54:00)
 * @since Java7
 */
public class SpringJUnit4ClassRunner extends AbstractSpringJUnit4ClassRunner {
    private static Logger log = Logs.slf4j();

    public SpringJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    public void run(RunNotifier notifier) {
        if (shouldRun()) {
            super.run(notifier);
        } else {
            log.info("test method should not run, " + getDescription() + ", " + getDescription().getTestClass() + "," + getDescription().getMethodName() + ", " + getDescription().getDisplayName());
        }
    }

    /**
     * @see org.apache.log4j.spi.Filter
     */
    private boolean shouldRun() {
        //        System.err.println(getChildren());//[org.junit.runners.model.FrameworkMethod@6568480d, org.junit.runners.model.FrameworkMethod@656f39ee, org.junit.runners.model.FrameworkMethod@5e0911fd]
        //        System.err.println(getRunnerAnnotations());//[Ljava.lang.annotation.Annotation;@619f9377
        //        System.err.println(getTestClass());//org.junit.runners.model.TestClass@338a9bb3
        //        System.err.println(getTestContextManager());//org.springframework.test.context.TestContextManager@50f41f34
        //        System.err.println(Errors.stackTrace(new Throwable("abc")));
        //        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        return true;
    }
}