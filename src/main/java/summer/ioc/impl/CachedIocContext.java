package summer.ioc.impl;

import java.util.HashMap;
import java.util.Map;

import summer.ioc.IocContext;

/**
 * @author li
 * @version 1 (2015年10月10日 下午12:06:34)
 * @since Java7
 */
@SuppressWarnings("unchecked")
public class CachedIocContext implements IocContext {
    private Map<Class<?>, Object> cacheByTypeMap = new HashMap<Class<?>, Object>();

    private Map<String, Object> cacheByIdMap = new HashMap<String, Object>();
    private Map<String, Object> cacheByTypeAndIdMap = new HashMap<String, Object>();

    private IocContext iocContext;

    public CachedIocContext(IocContext iocContext) {
        this.iocContext = iocContext;
    }

    public <T> T getBean(Class<T> type) {
        Object beanInstance = cacheByTypeMap.get(type);
        if (null == beanInstance) {
            synchronized (cacheByTypeMap) {
                beanInstance = cacheByTypeMap.get(type);
                if (null == beanInstance) {
                    T instance = iocContext.getBean(type);
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
                    T instance = iocContext.getBean(type, id);
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
                    Object instance = iocContext.getBean(id);
                    cacheByIdMap.put(id, instance);
                    beanInstance = instance;
                }
            }
        }
        return beanInstance;
    }
}