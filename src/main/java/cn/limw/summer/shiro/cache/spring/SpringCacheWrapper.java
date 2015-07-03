package cn.limw.summer.shiro.cache.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.ehcache.Ehcache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * @author li
 * @version 1 (2014年12月5日 下午5:11:10)
 * @since Java7
 */
public class SpringCacheWrapper implements Cache {
    private org.springframework.cache.Cache springCache;

    public SpringCacheWrapper(org.springframework.cache.Cache springCache) {
        if (springCache.getNativeCache() instanceof Ehcache) {
            this.springCache = springCache;
        } else {
            throw new UnsupportedOperationException("invoke spring cache abstract size method not supported");
        }
    }

    public Object get(Object key) throws CacheException {
        Object value = springCache.get(key);
        if (value instanceof SimpleValueWrapper) {
            return ((SimpleValueWrapper) value).get();
        }
        return value;
    }

    public Object put(Object key, Object value) throws CacheException {
        springCache.put(key, value);
        return value;
    }

    public Object remove(Object key) throws CacheException {
        springCache.evict(key);
        return null;
    }

    public void clear() throws CacheException {
        springCache.clear();
    }

    public int size() {
        Ehcache ehcache = (Ehcache) springCache.getNativeCache();
        return ehcache.getSize();
    }

    public Set keys() {
        Ehcache ehcache = (Ehcache) springCache.getNativeCache();
        return new HashSet(ehcache.getKeys());
    }

    public Collection values() {
        Ehcache ehcache = (Ehcache) springCache.getNativeCache();
        List keys = ehcache.getKeys();
        if (!CollectionUtils.isEmpty(keys)) {
            List values = new ArrayList(keys.size());
            for (Object key : keys) {
                Object value = get(key);
                if (value != null) {
                    values.add(value);
                }
            }
            return Collections.unmodifiableList(values);
        } else {
            return Collections.emptyList();
        }
    }
}
