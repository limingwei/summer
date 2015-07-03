package cn.limw.summer.spring.beans.factory.lazyinject;

import javassist.ClassPool;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import cn.limw.summer.spring.beans.factory.lazyinject.LazyInject.RequiredType;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年6月19日 下午4:37:22)
 * @since Java7
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LazyInjectUtil {
    private static final String $_LAZY_INJECT_SUFFIX = "_LazyInject_";

    public static String buildLazyInjectTypeName(Class<?> fieldType, ClassPool classPool) {
        String className = fieldType.getName() + $_LAZY_INJECT_SUFFIX;
        for (int i = 1;; i++) {
            if (null == classPool.getOrNull(className + i)) {
                return className + i;
            }
        }
    }

    public static Class<?> getBeanType(Object target) {
        Class<?> beanType = target.getClass();

        while (beanType.getName().contains("$$EnhancerBySpringCGLIB$$")) {
            beanType = beanType.getSuperclass();
        }
        return beanType;
    }

    public static final synchronized Object doGetLazyInjectDelegateTarget(ApplicationContext applicationContext, Class requiredType, LazyInject lazyInject) {
        try {
            Asserts.noNull(applicationContext, "applicationContext is null");

            String beanName = lazyInject.value();
            if (StringUtil.isEmpty(beanName)) {
                return applicationContext.getBean(requiredType);
            } else {
                return applicationContext.getBean(beanName, requiredType);
            }
        } catch (NoSuchBeanDefinitionException e) {
            if (RequiredType.TRUE.equals(lazyInject.required())) {
                throw e;
            } else {
                return null;
            }
        }
    }
}