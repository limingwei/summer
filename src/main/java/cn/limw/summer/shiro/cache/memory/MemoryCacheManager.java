package cn.limw.summer.shiro.cache.memory;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import cn.limw.summer.java.collection.SynchronizedMap;

/**
 * @author li
 * @version 1 (2015年6月5日 上午11:49:33)
 * @since Java7
 * @see cn.limw.summer.shiro.cache.redis.RedisShiroCacheManager
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MemoryCacheManager implements CacheManager {
    private Map<String, MemoryCache> memoryCacheMap = new SynchronizedMap(new HashMap<String, MemoryCache>());

    public synchronized <K, V> Cache<K, V> getCache(String name) throws CacheException {
        MemoryCache memoryCache = memoryCacheMap.get(name);
        if (null == memoryCache) {
            memoryCacheMap.put(name, memoryCache = new MemoryCache<K, V>());
        }
        return memoryCache;
    }
}