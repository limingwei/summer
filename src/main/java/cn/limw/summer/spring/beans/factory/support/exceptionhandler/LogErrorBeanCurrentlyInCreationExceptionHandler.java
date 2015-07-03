package cn.limw.summer.spring.beans.factory.support.exceptionhandler;

import java.util.Set;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年4月29日 下午2:35:46)
 * @since Java7
 */
public class LogErrorBeanCurrentlyInCreationExceptionHandler implements BeanCurrentlyInCreationHandler {
    public static final LogErrorBeanCurrentlyInCreationExceptionHandler INSTANCE = new LogErrorBeanCurrentlyInCreationExceptionHandler();

    private static final Logger log = Logs.slf4j();

    public void handleException(String beanName, Set<String> actualDependentBeans) {
        log.error(BeanCurrentlyInCreationHandlerUtil.beanCurrentlyInCreationExceptionMessage(beanName, actualDependentBeans));
    }
}