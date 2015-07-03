package cn.limw.summer.shiro.cache.nocache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

/**
 * @author li
 * @version 1 (2014年9月15日 上午10:47:56)
 * @since Java7
 * @see org.apache.shiro.cache.Cache
 */
public class NoCache<K, V> implements Cache<K, V> {
    public V get(K key) throws CacheException {
        return null;
    }

    public V put(K key, V value) throws CacheException {
        return null;
    }

    public V remove(K key) throws CacheException {
        return null;
    }

    public Set<K> keys() {
        return null;
    }

    public Collection<V> values() {
        return null;
    }

    public int size() {
        return 0;
    }

    public void clear() throws CacheException {}
}