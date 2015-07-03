package cn.limw.summer.dao.summer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author li
 * @version 1 (2015年6月24日 下午12:39:02)
 * @since Java7
 */
public class EntityPool {
    public static final EntityPool INSTANCE = new EntityPool();

    private EntityProducer entityProducer = new EntityProducer();

    private Map<Class, Class> cache = new HashMap<Class, Class>();

    public <T> Class<T> get(Class<T> type) {
        Class<T> entityType = (Class<T>) cache.get(type);
        if (null == entityType) {
            cache.put(type, entityType = entityProducer.produce(type));
        }
        return entityType;
    }
}