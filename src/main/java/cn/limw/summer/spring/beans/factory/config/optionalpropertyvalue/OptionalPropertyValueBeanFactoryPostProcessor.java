package cn.limw.summer.spring.beans.factory.config.optionalpropertyvalue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import cn.limw.summer.util.Mirrors;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年1月20日 上午11:36:09)
 * @since Java7
 */
public class OptionalPropertyValueBeanFactoryPostProcessor implements BeanFactoryPostProcessor, PriorityOrdered {
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 1;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanNames = beanFactory.getBeanDefinitionNames();
        for (String curName : beanNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(curName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (StringUtil.isEmpty(beanClassName)) {
                //
            } else if ((Mirrors.hasClass(beanClassName))) {
                MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();
                List<PropertyValue> propertyValues = new ArrayList<PropertyValue>(mutablePropertyValues.getPropertyValueList());
                for (PropertyValue propertyValue : propertyValues) {
                    //
                }
            }
        }
    }
}