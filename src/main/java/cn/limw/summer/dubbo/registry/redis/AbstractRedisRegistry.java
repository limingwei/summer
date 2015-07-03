package cn.limw.summer.dubbo.registry.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import cn.limw.summer.dubbo.registry.support.FailbackRegistry;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.NamedThreadFactory;
import com.alibaba.dubbo.registry.NotifyListener;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * @author li
 * @version 1 (2014年12月22日 下午2:26:18)
 * @since Java7
 */
public class AbstractRedisRegistry extends FailbackRegistry {
    private static final Logger logger = LoggerFactory.getLogger(AbstractRedisRegistry.class);

    private final ScheduledExecutorService expireExecutor = Executors.newScheduledThreadPool(1, new NamedThreadFactory("DubboRegistryExpireTimer", true));

    private final ScheduledFuture<?> expireFuture;

    private final String root;

    private final Map<String, JedisPool> jedisPools = new ConcurrentHashMap<String, JedisPool>();

    private final ConcurrentMap<String, RedisRegistryNotifier> notifiers = new ConcurrentHashMap<String, RedisRegistryNotifier>();

    private int reconnectPeriod;

    private final int expirePeriod;

    private volatile boolean admin = false;

    private boolean replicate;

    public AbstractRedisRegistry(URL url) {
        super(url);
        RedisRegistryUtil.assertNotAnyHost(url);
        GenericObjectPoolConfig config = RedisRegistryUtil.genericObjectPoolConfig(url);
        List<String> addresses = RedisRegistryUtil.getAddresses(url);

        for (String address : addresses) {
            JedisPool jedisPool = RedisRegistryUtil.initJedisPoolAndCheck(url, config, address);
            jedisPools.put(address, jedisPool);
        }

        this.replicate = RedisRegistryUtil.getReplicate(url);
        this.reconnectPeriod = url.getParameter(Constants.REGISTRY_RECONNECT_PERIOD_KEY, Constants.DEFAULT_REGISTRY_RECONNECT_PERIOD);
        this.root = RedisRegistryUtil.getGroup(url);
        this.expirePeriod = url.getParameter(Constants.SESSION_TIMEOUT_KEY, Constants.DEFAULT_SESSION_TIMEOUT);
        this.expireFuture = expireExecutor.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                try {
                    deferExpired(); // 延长过期时间
                } catch (Throwable t) { // 防御性容错
                    logger.error("Unexpected exception occur at defer expire time, cause: " + t.getMessage(), t);
                }
            }
        }, expirePeriod / 2, expirePeriod / 2, TimeUnit.MILLISECONDS);
    }

    private void deferExpired() {
        for (Map.Entry<String, JedisPool> entry : jedisPools.entrySet()) {
            JedisPool jedisPool = entry.getValue();
            try {
                Jedis jedis = jedisPool.getResource();
                try {
                    RedisRegistryUtil.publishToJedis(jedis, getRegistered(), root, expirePeriod);
                    if (admin) {
                        clean(jedis);
                    }
                    if (!replicate) {
                        break;//  如果服务器端已同步数据，只需写入单台机器
                    }
                } finally {
                    jedisPool.returnResource(jedis);
                }
            } catch (Throwable t) {
                logger.warn("Failed to write provider heartbeat to redis registry. registry: " + entry.getKey() + ", cause: " + t.getMessage(), t);
            }
        }
    }

    // 监控中心负责删除过期脏数据
    private void clean(Jedis jedis) {
        RedisRegistryUtil.clean(jedis, root);
    }

    public boolean isAvailable() {
        for (JedisPool jedisPool : jedisPools.values()) {
            try {
                Jedis jedis = jedisPool.getResource();
                try {
                    if (jedis.isConnected()) {
                        return true; // 至少需单台机器可用
                    }
                } finally {
                    jedisPool.returnResource(jedis);
                }
            } catch (Throwable t) {}
        }
        return false;
    }

    public void destroy() {
        super.destroy();
        try {
            expireFuture.cancel(true);
        } catch (Throwable t) {
            logger.warn(t.getMessage(), t);
        }
        try {
            for (RedisRegistryNotifier notifier : notifiers.values()) {
                notifier.shutdown();
            }
        } catch (Throwable t) {
            logger.warn(t.getMessage(), t);
        }
        for (Map.Entry<String, JedisPool> entry : jedisPools.entrySet()) {
            JedisPool jedisPool = entry.getValue();
            try {
                jedisPool.destroy();
            } catch (Throwable t) {
                logger.warn("Failed to destroy the redis registry client. registry: " + entry.getKey() + ", cause: " + t.getMessage(), t);
            }
        }
    }

    public void doRegister(URL url) {
        String key = RedisRegistryUtil.toCategoryPath(url, root);
        String value = url.toFullString();
        String expire = String.valueOf(System.currentTimeMillis() + expirePeriod);
        boolean success = false;
        RpcException exception = null;
        for (Map.Entry<String, JedisPool> entry : jedisPools.entrySet()) {
            JedisPool jedisPool = entry.getValue();
            try {
                Jedis jedis = jedisPool.getResource();
                try {
                    jedis.hset(key, value, expire);
                    jedis.publish(key, Constants.REGISTER);
                    success = true;
                    if (!replicate) {
                        break; //  如果服务器端已同步数据，只需写入单台机器
                    }
                } finally {
                    jedisPool.returnResource(jedis);
                }
            } catch (Throwable t) {
                exception = new RpcException("Failed to register service to redis registry. registry: " + entry.getKey() + ", service: " + url + ", cause: " + t.getMessage(), t);
            }
        }
        if (exception != null) {
            if (success) {
                logger.warn(exception.getMessage(), exception);
            } else {
                throw exception;
            }
        }
    }

    public void doUnregister(URL url) {
        String key = RedisRegistryUtil.toCategoryPath(url, root);
        String value = url.toFullString();
        RpcException exception = null;
        boolean success = false;
        for (Map.Entry<String, JedisPool> entry : jedisPools.entrySet()) {
            JedisPool jedisPool = entry.getValue();
            try {
                Jedis jedis = jedisPool.getResource();
                try {
                    jedis.hdel(key, value);
                    jedis.publish(key, Constants.UNREGISTER);
                    success = true;
                    if (!replicate) {
                        break; //  如果服务器端已同步数据，只需写入单台机器
                    }
                } finally {
                    jedisPool.returnResource(jedis);
                }
            } catch (Throwable t) {
                exception = new RpcException("Failed to unregister service to redis registry. registry: " + entry.getKey() + ", service: " + url + ", cause: " + t.getMessage(), t);
            }
        }
        if (exception != null) {
            if (success) {
                logger.warn(exception.getMessage(), exception);
            } else {
                throw exception;
            }
        }
    }

    public void doSubscribe(final URL url, final NotifyListener listener) {
        String service = RedisRegistryUtil.toServicePath(url, root);
        RedisRegistryNotifier notifier = notifiers.get(service);
        if (notifier == null) {
            RedisRegistryNotifier newNotifier = new RedisRegistryNotifier(service, this);
            notifiers.putIfAbsent(service, newNotifier);
            notifier = notifiers.get(service);
            if (notifier == newNotifier) {
                notifier.start();
            }
        }
        boolean success = false;
        RpcException exception = null;
        for (Map.Entry<String, JedisPool> entry : jedisPools.entrySet()) {
            JedisPool jedisPool = entry.getValue();
            try {
                Jedis jedis = jedisPool.getResource();
                try {
                    if (service.endsWith(Constants.ANY_VALUE)) {
                        admin = true;
                        Set<String> keys = jedis.keys(service);
                        if (keys != null && keys.size() > 0) {
                            Map<String, Set<String>> serviceKeys = RedisRegistryUtil.getServiceKeys(keys, root);
                            for (Set<String> sk : serviceKeys.values()) {
                                doNotify(jedis, sk, url, Arrays.asList(listener));
                            }
                        }
                    } else {
                        doNotify(jedis, jedis.keys(service + Constants.PATH_SEPARATOR + Constants.ANY_VALUE), url, Arrays.asList(listener));
                    }
                    success = true;
                    break; // 只需读一个服务器的数据
                } finally {
                    jedisPool.returnResource(jedis);
                }
            } catch (Throwable t) { // 尝试下一个服务器
                exception = new RpcException("Failed to subscribe service from redis registry. registry: " + entry.getKey() + ", service: " + url + ", cause: " + t.getMessage(), t);
            }
        }
        if (exception != null) {
            if (success) {
                logger.warn(exception.getMessage(), exception);
            } else {
                throw exception;
            }
        }
    }

    public void doNotify(Jedis jedis, String key) {
        for (Map.Entry<URL, Set<NotifyListener>> entry : new HashMap<URL, Set<NotifyListener>>(getSubscribed()).entrySet()) {
            doNotify(jedis, Arrays.asList(key), entry.getKey(), new HashSet<NotifyListener>(entry.getValue()));
        }
    }

    private void doNotify(Jedis jedis, Collection<String> keys, URL url, Collection<NotifyListener> listeners) {
        if (keys == null || keys.size() == 0 || listeners == null || listeners.size() == 0) {
            return;
        }
        long now = System.currentTimeMillis();
        List<URL> result = new ArrayList<URL>();
        List<String> categories = Arrays.asList(url.getParameter(Constants.CATEGORY_KEY, new String[0]));
        String consumerService = url.getServiceInterface();
        for (String key : keys) {
            if (!Constants.ANY_VALUE.equals(consumerService)) {
                String prvoiderService = RedisRegistryUtil.toServiceName(key, root);
                if (!prvoiderService.equals(consumerService)) {
                    continue;
                }
            }
            String category = RedisRegistryUtil.toCategoryName(key);
            if (!categories.contains(Constants.ANY_VALUE) && !categories.contains(category)) {
                continue;
            }

            Map<String, String> values = jedis.hgetAll(key);
            List<URL> urls = RedisRegistryUtil.getUrlsForDoNotify(url, now, values);

            if (urls.isEmpty()) {
                urls.add(RedisRegistryUtil.setUrlProperties(url, key, category, root));
            }
            result.addAll(urls);
            if (logger.isWarnEnabled()) {
                logger.warn("redis notify: " + key + " = " + urls);
            }
        }
        if (result == null || result.size() == 0) {
            return;
        }
        for (NotifyListener listener : listeners) {
            notify(url, listener, result);
        }
    }

    public void doUnsubscribe(URL url, NotifyListener listener) {
        // do nothing
    }

    public Map<String, JedisPool> getJedisPools() {
        return jedisPools;
    }

    public int getReconnectPeriod() {
        return reconnectPeriod;
    }
}