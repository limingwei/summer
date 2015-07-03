package cn.limw.summer.shiro.cache.nocache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @author li
 * @version 1 (2014年9月15日 上午10:37:28)
 * @since Java7
 * @see org.apache.shiro.cache.ehcache.EhCacheManager
 */
public class NoCacheManager implements CacheManager {
    private static final Cache NO_CACHE = new NoCache();

    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return NO_CACHE;
    }
}