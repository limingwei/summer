package cn.limw.summer.shiro.cache.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.SerializationUtils;

import cn.limw.summer.shiro.cache.ICached;

/**
 * @author lgb
 * @version 1 (2014年9月10日上午10:17:44)
 * @since Java7
 */
public class RedisCachedImpl implements ICached {
    private int expire = -1;//-1不过期

    private RedisTemplate<String, Object> redisTemplate;

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String deleteCached(final byte[] key) throws Exception {
        redisTemplate.execute(new RedisCallback<Object>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.del(key);
                return null;
            }
        });
        return null;
    }

    public Object updateCached(final byte[] key, final byte[] value, final Long expireSec) throws Exception {
        return (String) redisTemplate.execute(new RedisCallback<Object>() {
            public String doInRedis(final RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (expireSec != null) {
                    connection.expire(key, expireSec);
                } else {
                    connection.expire(key, expire);
                }
                return new String(key);
            }
        });
    }

    public Object getCached(final byte[] key) throws Exception {
        return redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] bs = connection.get(key);
                return SerializationUtils.deserialize(bs);
            }
        });
    }

    public Set getKeys(final byte[] keys) throws Exception {
        return redisTemplate.execute(new RedisCallback<Set>() {
            public Set doInRedis(RedisConnection connection) throws DataAccessException {
                Set<byte[]> setByte = connection.keys(keys);
                if (setByte == null || setByte.size() < 1) {
                    return null;
                }
                Set set = new HashSet();
                for (byte[] key : setByte) {
                    byte[] bs = connection.get(key);
                    set.add(SerializationUtils.deserialize(bs));
                }
                return set;
            }
        });
    }

    public Set getHashKeys(final byte[] key) throws Exception {
        return (Set) redisTemplate.execute(new RedisCallback<Set>() {
            public Set doInRedis(RedisConnection connection) throws DataAccessException {
                Set<byte[]> hKeys = connection.hKeys(key);
                if (hKeys == null || hKeys.size() > 1) {
                    return null;
                }
                Set set = new HashSet();
                for (byte[] bs : hKeys) {
                    set.add(SerializationUtils.deserialize(bs));
                }
                return set;
            }
        });
    }

    public Boolean updateHashCached(final byte[] key, final byte[] mapkey, final byte[] value, final Long expire) throws Exception {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                Boolean hSet = connection.hSet(key, mapkey, value);
                return hSet;
            }
        });
    }

    public Object getHashCached(final byte[] key, final byte[] mapkey) throws Exception {
        return redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] hGet = connection.hGet(key, mapkey);
                return SerializationUtils.deserialize(hGet);
            }
        });
    }

    public Long deleteHashCached(final byte[] key, final byte[] mapkey) throws Exception {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Long hDel = connection.hDel(key, mapkey);
                return hDel;
            }
        });
    }

    public Long getHashSize(final byte[] key) throws Exception {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Long len = connection.hLen(key);
                return len;
            }
        });
    }

    public List getHashValues(final byte[] key) throws Exception {
        return redisTemplate.execute(new RedisCallback<List>() {
            public List doInRedis(RedisConnection connection) throws DataAccessException {
                List<byte[]> hVals = connection.hVals(key);
                if (hVals == null || hVals.size() < 1) {
                    return null;
                }
                List list = new ArrayList();
                for (byte[] bs : hVals) {
                    list.add(SerializationUtils.deserialize(bs));
                }
                return list;
            }
        });
    }

    public Long getDBSize() throws Exception {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Long len = connection.dbSize();
                return len;
            }
        });
    }

    public void clearDB() throws Exception {
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return null;
            }
        });
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}