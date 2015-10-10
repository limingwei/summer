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
        return iocContext.getBean(type, id);
    }

    public Object getBean(String id) {
        return iocContext.getBean(id);
    }
}