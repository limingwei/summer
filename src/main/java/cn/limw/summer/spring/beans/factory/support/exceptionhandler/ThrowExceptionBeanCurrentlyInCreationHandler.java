package cn.limw.summer.spring.beans.factory.support.exceptionhandler;

import java.util.Set;

import org.springframework.beans.factory.BeanCurrentlyInCreationException;

/**
 * @author li
 * @version 1 (2015年4月29日 下午2:35:30)
 * @since Java7
 */
public class ThrowExceptionBeanCurrentlyInCreationHandler implements BeanCurrentlyInCreationHandler {
    public static final ThrowExceptionBeanCurrentlyInCreationHandler INSTANCE = new ThrowExceptionBeanCurrentlyInCreationHandler();

    public void handleException(String beanName, Set<String> actualDependentBeans) {
        throw new BeanCurrentlyInCreationException(beanName, BeanCurrentlyInCreationHandlerUtil.beanCurrentlyInCreationExceptionMessage(beanName, actualDependentBeans));
    }
}