package cn.limw.summer.dao.summer;

import java.util.HashMap;
import java.util.Map;

import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2015年6月23日 下午3:21:14)
 * @since Java7
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EntityMakerPool {
    public static final EntityMakerPool INSTANCE = new EntityMakerPool();

    private EntityMakerProducer entityMakerProducer = new EntityMakerProducer();

    private Map<Class, EntityMaker> cache = new HashMap<Class, EntityMaker>();

    public <T> EntityMaker<T> get(Class<T> type) {
        EntityMaker<T> entityMaker = (EntityMaker<T>) cache.get(type);
        if (null == entityMaker) {
            EntityPool.INSTANCE.get(type);
            Class<EntityMaker<T>> entityMakerType = entityMakerProducer.produce(type);
            cache.put(type, entityMaker = Mirrors.newInstance(entityMakerType));
        }
        return entityMaker;
    }
}