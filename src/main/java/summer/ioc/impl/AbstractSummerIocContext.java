package summer.ioc.impl;

import java.util.HashMap;
import java.util.Map;

import summer.ioc.IocContext;
import summer.ioc.impl.util.SummerIocContextUtil;

/**
 * @author li
 * @version 1 (2015年10月11日 下午5:43:24)
 * @since Java7
 */
@SuppressWarnings("unchecked")
public abstract class AbstractSummerIocContext implements IocContext {
    private Map<String, Object> cacheByTypeAndIdMap = new HashMap<String, Object>();

    private Map<String, Boolean> containsCacheByTypeAndIdMap = new HashMap<String, Boolean>();

    public synchronized Boolean containsBean(Class<?> type) {
        return containsBean(type, null);
    }

    public synchronized Boolean containsBean(String id) {
        return containsBean(Object.class, id);
    }

    public synchronized Boolean containsBean(Class<?> type, String id) {
        String key = type + "," + id;
        Boolean bool = containsCacheByTypeAndIdMap.get(key);
        if (null == bool) {
            containsCacheByTypeAndIdMap.put(key, bool = SummerIocContextUtil.containsBean(getBeanDefinitions(), type, id));
        }
        return bool;
    }

    public Object getBean(String id) {
        return getBean(Object.class, id);
    }

    public <T> T getBean(Class<T> type) {
        return getBean(type, null);
    }

    public synchronized <T> T getBean(Class<T> type, String id) {
        String key = type + "," + id;
        Object beanInstance = cacheByTypeAndIdMap.get(key);
        if (null == beanInstance) {
            T instance = doGetBean(type, id);
            cacheByTypeAndIdMap.put(key, instance);
            beanInstance = instance;
        }
        return (T) beanInstance;
    }

    public abstract <T> T doGetBean(Class<T> type, String id);
}