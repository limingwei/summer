package summer.ioc.impl;

import java.util.HashMap;
import java.util.Map;

import summer.ioc.IocContext;

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
        Object beanInstance = cacheByTypeAndIdMap.get(type + "," + id);
        if (null == beanInstance) {
            synchronized (cacheByTypeAndIdMap) {
                beanInstance = cacheByTypeAndIdMap.get(type + "," + id);
                if (null == beanInstance) {
                    T instance = doGetBean(type, id);
                    cacheByTypeAndIdMap.put(type + "," + id, instance);
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