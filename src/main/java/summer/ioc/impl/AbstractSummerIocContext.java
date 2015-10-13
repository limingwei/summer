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
    private Map<Class<?>, Object> cacheByTypeMap = new HashMap<Class<?>, Object>();

    private Map<String, Object> cacheByIdMap = new HashMap<String, Object>();

    private Map<String, Object> cacheByTypeAndIdMap = new HashMap<String, Object>();

    private Map<Class<?>, Boolean> containsCacheByTypeMap = new HashMap<Class<?>, Boolean>();

    private Map<String, Boolean> containsCacheByIdMap = new HashMap<String, Boolean>();

    private Map<String, Boolean> containsCacheByTypeAndIdMap = new HashMap<String, Boolean>();

    public synchronized Boolean containsBean(Class<?> type) {
        Boolean bool = containsCacheByTypeMap.get(type);
        if (null == bool) {
            containsCacheByTypeMap.put(type, bool = SummerIocContextUtil.containsBean(getBeanDefinitions(), type));
        }
        return bool;
    }

    public synchronized Boolean containsBean(String id) {
        Boolean bool = containsCacheByIdMap.get(id);
        if (null == bool) {
            containsCacheByIdMap.put(id, bool = SummerIocContextUtil.containsBean(getBeanDefinitions(), id));
        }
        return bool;
    }

    public synchronized Boolean containsBean(Class<?> type, String id) {
        String key = type + "," + id;
        Boolean bool = containsCacheByTypeAndIdMap.get(key);
        if (null == bool) {
            containsCacheByTypeAndIdMap.put(key, bool = SummerIocContextUtil.containsBean(getBeanDefinitions(), type, id));
        }
        return bool;
    }

    public <T> T getBean(Class<T> type) {
        Object beanInstance = cacheByTypeMap.get(type);
        if (null == beanInstance) {
            synchronized (cacheByTypeMap) {
                beanInstance = cacheByTypeMap.get(type);
                if (null == beanInstance) {
                    T instance = doGetBean(type);
                    cacheByTypeMap.put(type, instance);
                    beanInstance = instance;
                }
            }
        }
        return (T) beanInstance;
    }

    public <T> T getBean(Class<T> type, String id) {
        String key = type + "," + id;
        Object beanInstance = cacheByTypeAndIdMap.get(key);
        if (null == beanInstance) {
            synchronized (cacheByTypeAndIdMap) {
                beanInstance = cacheByTypeAndIdMap.get(key);
                if (null == beanInstance) {
                    T instance = doGetBean(type, id);
                    cacheByTypeAndIdMap.put(key, instance);
                    beanInstance = instance;
                }
            }
        }
        return (T) beanInstance;
    }

    public Object getBean(String id) {
        Object beanInstance = cacheByIdMap.get(id);
        if (null == beanInstance) {
            synchronized (cacheByIdMap) {
                beanInstance = cacheByIdMap.get(id);
                if (null == beanInstance) {
                    Object instance = doGetBean(id);
                    cacheByIdMap.put(id, instance);
                    beanInstance = instance;
                }
            }
        }
        return beanInstance;
    }

    public abstract Object doGetBean(String id);

    public abstract <T> T doGetBean(Class<T> type);

    public abstract <T> T doGetBean(Class<T> type, String id);
}