package cn.limw.summer.spring.util;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.limw.summer.util.Mirrors;

/**
 * @author li
 */
public class SpringUtil {
    public static <T> T getBeanByTypeAndActualTypes(ApplicationContext applicationContext, Class<T> beanType, Type... actualTypes) {
        Map<String, T> map = applicationContext.getBeansOfType(beanType);
        Set<Entry<String, T>> set = map.entrySet();
        for (Entry<String, T> entry : set) {
            T bean = entry.getValue();
            Class<?> type = bean.getClass();
            while (type.getName().contains("$$EnhancerBySpringCGLIB$$")) {
                type = type.getSuperclass();
            }
            if (Mirrors.equals(Mirrors.getActualTypeArguments(type), actualTypes)) {
                return bean;
            }
        }
        return null;
    }

    public static DataSource getBean(ApplicationContextAware applicationContextAware, String beanId) {
        return null;
    }
}