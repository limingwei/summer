package cn.limw.summer.spring.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * @author li
 * @version 1 (2015年4月29日 下午1:47:44)
 * @since Java7
 */
public class CreateBeanObjectFactory implements ObjectFactory<Object> {
    private String beanName;

    private RootBeanDefinition rootBeanDefinition;

    private Object[] args;

    private DefaultListableBeanFactory defaultListableBeanFactory;

    public CreateBeanObjectFactory(String beanName, RootBeanDefinition rootBeanDefinition, Object[] args, DefaultListableBeanFactory defaultListableBeanFactory) {
        this.beanName = beanName;
        this.rootBeanDefinition = rootBeanDefinition;
        this.args = args;

        this.defaultListableBeanFactory = defaultListableBeanFactory;
    }

    public Object getObject() throws BeansException {
        try {
            return defaultListableBeanFactory.callCreateBean(beanName, rootBeanDefinition, args);
        } catch (BeansException ex) {
            defaultListableBeanFactory.destroySingleton(beanName);
            throw ex;
        }
    }
}