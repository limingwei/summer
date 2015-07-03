package cn.limw.summer.spring.beans.factory.support.exceptionhandler;

import java.util.Set;

/**
 * @author li
 * @version 1 (2015年4月29日 下午2:32:50)
 * @since Java7
 */
public interface BeanCurrentlyInCreationHandler {
    public void handleException(String beanName, Set<String> actualDependentBeans);
}