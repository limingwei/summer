package cn.limw.summer.dubbo.registry.redis;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

/**
 * @author li
 * @version 1 (2014年12月24日 下午5:16:41)
 * @since Java7
 */
public class RedisRegistryNotifier extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(RedisRegistryNotifier.class);

    private final String service;

    private volatile Jedis jedis;

    private volatile boolean first = true;

    private volatile boolean running = true;

    private final AtomicInteger connectSkip = new AtomicInteger();

    private final AtomicInteger connectSkiped = new AtomicInteger();

    private final Random random = new Random();

    private volatile int connectRandom;

    private AbstractRedisRegistry abstractRedisRegistry;

    public RedisRegistryNotifier(String service, AbstractRedisRegistry abstractRedisRegistry) {
        super.setDaemon(true);
        super.setName("DubboRedisSubscribe");
        this.service = service;
        this.abstractRedisRegistry = abstractRedisRegistry;
    }

    private void resetSkip() {
        connectSkip.set(0);
        connectSkiped.set(0);
        connectRandom = 0;
    }

    private boolean isSkip() {
        int skip = connectSkip.get(); // 跳过次数增长
        if (skip >= 10) { // 如果跳过次数增长超过10，取随机数
            if (connectRandom == 0) {
                connectRandom = random.nextInt(10);
            }
            skip = 10 + connectRandom;
        }
        if (connectSkiped.getAndIncrement() < skip) { // 检查跳过次数
            return true;
        }
        connectSkip.incrementAndGet();
        connectSkiped.set(0);
        connectRandom = 0;
        return false;
    }

    public void run() {
        while (running) {
            try {
                if (!isSkip()) {
                    try {
                        for (Map.Entry<String, JedisPool> entry : getAbstractRedisRegistry().getJedisPools().entrySet()) {
                            JedisPool jedisPool = entry.getValue();
                            try {
                                jedis = jedisPool.getResource();
                                try {
                                    if (service.endsWith(Constants.ANY_VALUE)) {
                                        if (!first) {
                                            first = false;
                                            Set<String> keys = jedis.keys(service);
                                            if (keys != null && keys.size() > 0) {
                                                for (String s : keys) {
                                                    getAbstractRedisRegistry().doNotify(jedis, s);
                                                }
                                            }
                                            resetSkip();
                                        }
                                        jedis.psubscribe(new RedisRegistryNotifySub(jedisPool, getAbstractRedisRegistry()), service); // 阻塞
                                    } else {
                                        if (!first) {
                                            first = false;
                                            getAbstractRedisRegistry().doNotify(jedis, service);
                                            resetSkip();
                                        }
                                        jedis.psubscribe(new RedisRegistryNotifySub(jedisPool, getAbstractRedisRegistry()), service + Constants.PATH_SEPARATOR + Constants.ANY_VALUE); // 阻塞
                                    }
                                    break;
                                } finally {
                                    jedisPool.returnBrokenResource(jedis);
                                }
                            } catch (Throwable t) { // 重试另一台
                                logger.warn("Failed to subscribe service from redis registry. registry: " + entry.getKey() + ", cause: " + t.getMessage(), t);
                            }
                        }
                    } catch (Throwable t) {
                        logger.error(t.getMessage(), t);
                        sleep(getAbstractRedisRegistry().getReconnectPeriod());
                    }
                }
            } catch (Throwable t) {
                logger.error(t.getMessage(), t);
            }
        }
    }

    public void shutdown() {
        try {
            running = false;
            jedis.disconnect();
        } catch (Throwable t) {
            logger.warn(t.getMessage(), t);
        }
    }

    public AbstractRedisRegistry getAbstractRedisRegistry() {
        return this.abstractRedisRegistry;
    }
}