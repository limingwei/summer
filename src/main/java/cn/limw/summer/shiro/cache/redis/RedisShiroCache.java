package cn.limw.summer.shiro.cache.redis;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.util.SerializationUtils;

import cn.limw.summer.shiro.cache.ICached;
import cn.limw.summer.util.Asserts;

/**
 * @author lgb
 * @version 1 (2014年9月10日上午10:27:13)
 * @since Java7
 */
public class RedisShiroCache<K, V> implements Cache<K, V> {
    private String name;

    private ICached cached;

    public ICached getCached() {
        return Asserts.noNull(cached);
    }

    public RedisShiroCache(String name, ICached cached) {
        this.name = name;
        this.cached = cached;
    }

    private byte[] getByteName() {
        return name.getBytes();
    }

    /**
     * 获得byte[]型的key
     * @param key
     * @return
     */
    private byte[] getByteKey(K key) {
        if (key instanceof String) {
            String preKey = key.toString();
            return preKey.getBytes();
        } else {
            return SerializationUtils.serialize(key);
        }
    }

    public V get(K key) throws CacheException {
        try {
            if (null == key) {
                return null;
            } else {
                V value = (V) getCached().getHashCached(getByteName(), getByteKey(key));
                return value;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    public V put(K key, V value) throws CacheException {
        try {
            getCached().updateHashCached(getByteName(), getByteKey(key), SerializationUtils.serialize(value), null);
            return value;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    public V remove(K key) throws CacheException {
        try {
            V previous = get(key);
            getCached().deleteHashCached(getByteName(), getByteKey(key));
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    public void clear() throws CacheException {
        try {
            getCached().deleteCached(getByteName());
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    public int size() {
        try {
            Long longSize = new Long(getCached().getHashSize(getByteName()));
            return longSize.intValue();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    public Set<K> keys() {
        try {
            Set<K> keys = getCached().getHashKeys(getByteName());
            return keys;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    public Collection<V> values() {
        try {
            Collection<V> values = getCached().getHashValues(getByteName());
            return values;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }
}