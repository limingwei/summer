package cn.limw.summer.shiro.cache.memory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import cn.limw.summer.java.collection.SynchronizedMap;

/**
 * @author li
 * @version 1 (2015年6月5日 上午11:51:04)
 * @since Java7
 * @see RedisShiroCache<K, V>
 */
public class MemoryCache<K, V> implements Cache<K, V> {
    private Map map = new SynchronizedMap(new HashMap());

    public V get(K key) throws CacheException {
        return (V) map.get(key);
    }

    public V put(K key, V value) throws CacheException {
        return (V) map.put(key, value);
    }

    public V remove(K key) throws CacheException {
        return (V) map.remove(key);
    }

    public void clear() throws CacheException {
        map.clear();
    }

    public int size() {
        return map.size();
    }

    public Set<K> keys() {
        return map.keySet();
    }

    public Collection<V> values() {
        return map.values();
    }
}