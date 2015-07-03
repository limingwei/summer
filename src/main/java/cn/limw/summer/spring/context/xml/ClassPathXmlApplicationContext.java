package cn.limw.summer.spring.context.xml;

import cn.limw.summer.spring.beans.factory.support.DefaultListableBeanFactory;
import cn.limw.summer.spring.beans.factory.support.exceptionhandler.BeanCurrentlyInCreationHandler;

/**
 * @author li
 * @version 1 (2015年4月28日 上午11:00:20)
 * @since Java7
 */
public class ClassPathXmlApplicationContext extends AbstractClassPathXmlApplicationContext {
    public static final ThreadLocal<BeanCurrentlyInCreationHandler> BEAN_CURRENTLY_IN_CREATION_HANDLER_THREAD_LOCAL = new ThreadLocal<BeanCurrentlyInCreationHandler>();

    public ClassPathXmlApplicationContext(String[] configLocations) {
        super(configLocations);
    }

    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory(getInternalParentBeanFactory());
    }
}