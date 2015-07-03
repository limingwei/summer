package cn.limw.summer.dubbo.registry.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

/**
 * @author li
 * @version 1 (2014年12月24日 下午5:18:03)
 * @since Java7
 */
public class RedisRegistryNotifySub extends JedisPubSub {
    private static final Logger logger = LoggerFactory.getLogger(RedisRegistryNotifySub.class);

    private final JedisPool jedisPool;

    private AbstractRedisRegistry abstractRedisRegistry;

    public RedisRegistryNotifySub(JedisPool jedisPool, AbstractRedisRegistry abstractRedisRegistry) {
        this.jedisPool = jedisPool;
        this.abstractRedisRegistry = abstractRedisRegistry;
    }

    public void onMessage(String key, String msg) {
        if (logger.isDebugEnabled()) { // 本来是 info
            logger.debug("redis event: " + key + " = " + msg);
        }

        if (msg.equals(Constants.REGISTER) || msg.equals(Constants.UNREGISTER)) {
            try {
                Jedis jedis = jedisPool.getResource();
                try {
                    getAbstractRedisRegistry().doNotify(jedis, key);
                } finally {
                    jedisPool.returnResource(jedis);
                }
            } catch (Throwable t) { // TODO 通知失败没有恢复机制保障
                logger.error(t.getMessage(), t);
            }
        }
    }

    public void onPMessage(String pattern, String key, String msg) {
        onMessage(key, msg);
    }

    public void onSubscribe(String key, int num) {}

    public void onPSubscribe(String pattern, int num) {}

    public void onUnsubscribe(String key, int num) {}

    public void onPUnsubscribe(String pattern, int num) {}

    public AbstractRedisRegistry getAbstractRedisRegistry() {
        return this.abstractRedisRegistry;
    }
}