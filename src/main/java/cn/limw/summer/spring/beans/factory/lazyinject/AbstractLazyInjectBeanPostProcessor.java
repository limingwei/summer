package cn.limw.summer.spring.beans.factory.lazyinject;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;

import cn.limw.summer.spring.beans.factory.config.AbstractInstantiationAwareBeanPostProcessor;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2015年6月19日 上午9:06:57)
 * @since Java7
 */
public class AbstractLazyInjectBeanPostProcessor extends AbstractInstantiationAwareBeanPostProcessor {
    private static final Logger log = Logs.slf4j();

    static final List<LazyInjectSupport> LAZY_INJECT_SUPPORTS = new ArrayList<LazyInjectSupport>();

    public final synchronized PropertyValues postProcessPropertyValues(PropertyValues propertyValues, PropertyDescriptor[] propertyDescriptors, Object target, String beanName) throws BeansException {
        Class<?> beanType = LazyInjectUtil.getBeanType(target);

        List<Field> fields = Mirrors.getAllFields(beanType);
        for (Field field : fields) {
            LazyInject lazyInject = field.getAnnotation(LazyInject.class);

            if (null != lazyInject) {
                log.info("LazyInject:" + field);
                LazyInjectSupport lazyInjectSupport = newLazyInjectSubclassInstance(field, lazyInject);
                LAZY_INJECT_SUPPORTS.add(lazyInjectSupport);
                Mirrors.setFieldValue(target, field, lazyInjectSupport);
            }
        }
        return propertyValues;
    }

    private LazyInjectSupport newLazyInjectSubclassInstance(Field field, LazyInject lazyInject) {
        Class<?> lazyInjectSubclassType = LazyInjectClassBuilder.buildLazyInjectSubclass(field, lazyInject);

        LazyInjectSupport lazyInjectSupport = (LazyInjectSupport) Mirrors.newInstance(lazyInjectSubclassType);
        LazyInjectDelegateHolder lazyInjectDelegateHolder = lazyInjectSupport._getLazyInjectDelegateHolder_();

        lazyInjectDelegateHolder.setLazyInject(lazyInject);
        lazyInjectDelegateHolder.setRequiredType(field.getType());
        return lazyInjectSupport;
    }
}