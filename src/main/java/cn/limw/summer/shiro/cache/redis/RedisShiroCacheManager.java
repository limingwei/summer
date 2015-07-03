package cn.limw.summer.shiro.cache.redis;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import cn.limw.summer.shiro.cache.ICached;
import cn.limw.summer.util.Asserts;

/**
 * @author lgb
 * @version 1 (2014年9月10日上午10:28:58)
 * @since Java7
 */
public class RedisShiroCacheManager extends AbstractCacheManager {
    private ICached cached;

    public ICached getCached() {
        return cached;
    }

    public void setCached(ICached cached) {
        this.cached = cached;
    }

    protected Cache createCache(String name) throws CacheException {
        return new RedisShiroCache<String, Object>(name, Asserts.noNull(getCached()));
    }
}