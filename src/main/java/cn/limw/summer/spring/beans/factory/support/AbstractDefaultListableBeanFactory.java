package cn.limw.summer.spring.beans.factory.support;

import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2015年4月28日 上午11:10:04)
 * @since Java7
 */
public class AbstractDefaultListableBeanFactory extends DefaultListableBeanFactory {
    public AbstractDefaultListableBeanFactory(BeanFactory beanFactory) {
        super(beanFactory);
    }

    public Boolean getAllowCircularReferences() {
        return (Boolean) Mirrors.getFieldValue(this, "allowCircularReferences");
    }

    public Boolean getAllowRawInjectionDespiteWrapping() {
        return (Boolean) Mirrors.getFieldValue(this, "allowRawInjectionDespiteWrapping");
    }

    public Map<String, BeanWrapper> getFactoryBeanInstanceCache() {
        return (Map<String, BeanWrapper>) Mirrors.getFieldValue(this, "factoryBeanInstanceCache");
    }
}