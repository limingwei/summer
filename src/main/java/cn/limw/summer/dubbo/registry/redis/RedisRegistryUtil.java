package cn.limw.summer.dubbo.registry.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import cn.limw.summer.jedis.util.JedisUtil;
import cn.limw.summer.util.Logs;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.UrlUtils;

/**
 * @author li
 * @version 1 (2014年12月24日 下午5:32:46)
 * @since Java7
 */
public class RedisRegistryUtil {
    private final static String DEFAULT_ROOT = "dubbo";

    private static final int DEFAULT_REDIS_PORT = 6379;

    private static final Logger logger = Logs.slf4j();

    public static GenericObjectPoolConfig genericObjectPoolConfig(URL url) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();

        config.setTestOnBorrow(url.getParameter("test.on.borrow", true));
        config.setTestOnReturn(url.getParameter("test.on.return", false));
        config.setTestWhileIdle(url.getParameter("test.while.idle", false));
        if (url.getParameter("max.idle", 0) > 0)
            config.setMaxIdle(url.getParameter("max.idle", 0));
        if (url.getParameter("min.idle", 0) > 0)
            config.setMaxIdle(url.getParameter("min.idle", 0));
        if (url.getParameter("max.active", 0) > 0)
            config.setMaxTotal(url.getParameter("max.active", 0));
        if (url.getParameter("max.wait", url.getParameter("timeout", 0)) > 0)
            config.setMaxWaitMillis(url.getParameter("max.wait", url.getParameter("timeout", 0)));
        if (url.getParameter("num.tests.per.eviction.run", 0) > 0)
            config.setNumTestsPerEvictionRun(url.getParameter("num.tests.per.eviction.run", 0));
        if (url.getParameter("time.between.eviction.runs.millis", 0) > 0)
            config.setTimeBetweenEvictionRunsMillis(url.getParameter("time.between.eviction.runs.millis", 0));
        if (url.getParameter("min.evictable.idle.time.millis", 0) > 0)
            config.setMinEvictableIdleTimeMillis(url.getParameter("min.evictable.idle.time.millis", 0));
        return config;
    }

    public static String toServicePath(String categoryPath, String root) {
        int i;
        if (categoryPath.startsWith(root)) {
            i = categoryPath.indexOf(Constants.PATH_SEPARATOR, root.length());
        } else {
            i = categoryPath.indexOf(Constants.PATH_SEPARATOR);
        }
        return i > 0 ? categoryPath.substring(0, i) : categoryPath;
    }

    public static String toServicePath(URL url, String root) {
        return root + url.getServiceInterface();
    }

    public static String toCategoryPath(URL url, String root) {
        return toServicePath(url, root) + Constants.PATH_SEPARATOR + url.getParameter(Constants.CATEGORY_KEY, Constants.DEFAULT_CATEGORY);
    }

    public static String toCategoryName(String categoryPath) {
        int i = categoryPath.lastIndexOf(Constants.PATH_SEPARATOR);
        return i > 0 ? categoryPath.substring(i + 1) : categoryPath;
    }

    public static String toServiceName(String categoryPath, String root) {
        String servicePath = RedisRegistryUtil.toServicePath(categoryPath, root);
        return servicePath.startsWith(root) ? servicePath.substring(root.length()) : servicePath;
    }

    public static List<URL> getUrlsForDoNotify(URL url, long now, Map<String, String> values) {
        List<URL> urls = new ArrayList<URL>();
        if (values != null && values.size() > 0) {
            for (Map.Entry<String, String> entry : values.entrySet()) {
                URL u = URL.valueOf(entry.getKey());
                if (!u.getParameter(Constants.DYNAMIC_KEY, true) || Long.parseLong(entry.getValue()) >= now) {
                    if (UrlUtils.isMatch(url, u)) {
                        urls.add(u);
                    }
                }
            }
        }
        return urls;
    }

    public static String getGroup(URL url) {
        String group = url.getParameter(Constants.GROUP_KEY, DEFAULT_ROOT);
        if (!group.startsWith(Constants.PATH_SEPARATOR)) {
            group = Constants.PATH_SEPARATOR + group;
        }
        if (!group.endsWith(Constants.PATH_SEPARATOR)) {
            group = group + Constants.PATH_SEPARATOR;
        }
        return group;
    }

    public static String getCluster(URL url) {
        String cluster = url.getParameter("cluster", "failover");
        if (!"failover".equals(cluster) && !"replicate".equals(cluster)) {
            throw new IllegalArgumentException("Unsupported redis cluster: " + cluster + ". The redis cluster only supported failover or replicate.");
        }
        return cluster;
    }

    public static boolean getReplicate(URL url) {
        return "replicate".equals(getCluster(url));
    }

    public static void assertNotAnyHost(URL url) {
        if (url.isAnyHost()) {
            throw new IllegalStateException("registry address == null");
        }
    }

    public static List<String> getAddresses(URL url) {
        List<String> addresses = new ArrayList<String>();
        addresses.add(url.getAddress());
        String[] backups = url.getParameter(Constants.BACKUP_KEY, new String[0]);
        if (backups != null && backups.length > 0) {
            addresses.addAll(Arrays.asList(backups));
        }
        return addresses;
    }

    public static void publishToJedis(Jedis jedis, Set<URL> registered, String root, long expirePeriod) {
        for (URL url : new HashSet<URL>(registered)) {
            if (url.getParameter(Constants.DYNAMIC_KEY, true)) {
                String key = RedisRegistryUtil.toCategoryPath(url, root);
                if (jedis.hset(key, url.toFullString(), String.valueOf(System.currentTimeMillis() + expirePeriod)) == 1) {
                    jedis.publish(key, Constants.REGISTER);
                }
            }
        }
    }

    public static Boolean delete(Jedis jedis, String key, Map<String, String> values) {
        long now = System.currentTimeMillis();
        Boolean delete = false;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            URL url = URL.valueOf(entry.getKey());
            if (url.getParameter(Constants.DYNAMIC_KEY, true)) {
                long expire = Long.parseLong(entry.getValue());
                if (expire < now) {
                    jedis.hdel(key, entry.getKey());
                    delete = true;
                    if (logger.isWarnEnabled()) {
                        logger.warn("Delete expired key: " + key + " -> value: " + entry.getKey() + ", expire: " + new Date(expire) + ", now: " + new Date(now));
                    }
                }
            }
        }
        return delete;
    }

    public static void clean(Jedis jedis, String root) {
        Set<String> keys = jedis.keys(root + Constants.ANY_VALUE);

        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                Map<String, String> values = jedis.hgetAll(key);
                if (values != null && values.size() > 0) {
                    if (delete(jedis, key, values)) {
                        jedis.publish(key, Constants.UNREGISTER);
                    }
                }
            }
        }
    }

    public static URL setUrlProperties(URL url, String key, String category, String root) {
        return url.setProtocol(Constants.EMPTY_PROTOCOL)
                .setAddress(Constants.ANYHOST_VALUE)
                .setPath(RedisRegistryUtil.toServiceName(key, root))
                .addParameter(Constants.CATEGORY_KEY, category);
    }

    public static Map<String, Set<String>> getServiceKeys(Set<String> keys, String root) {
        Map<String, Set<String>> serviceKeys = new HashMap<String, Set<String>>();
        for (String key : keys) {
            String serviceKey = RedisRegistryUtil.toServicePath(key, root);
            Set<String> sk = serviceKeys.get(serviceKey);
            if (sk == null) {
                sk = new HashSet<String>();
                serviceKeys.put(serviceKey, sk);
            }
            sk.add(key);
        }
        return serviceKeys;
    }

    public static JedisPool initJedisPool(URL url, GenericObjectPoolConfig config, String address) {
        String host = host(address);
        int port = port(address);
        int timeout = url.getParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT);
        String password = url.getPassword();
        return new JedisPool(config, host, port, timeout, password);
    }

    public static JedisPool initJedisPoolAndCheck(URL url, GenericObjectPoolConfig config, String address) {
        JedisPool jedisPool = initJedisPool(url, config, address);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (RuntimeException e) {
            logger.error("jedisPool.getResource() error host=" + host(address) + ", port=" + port(address) + ", timeout=" + url.getParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT) + ", password=" + url.getPassword(), e);
            throw new RuntimeException("jedisPool.getResource() error host=" + host(address) + ", port=" + port(address) + ", timeout=" + url.getParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT) + ", password=" + url.getPassword() + " " + e.getMessage(), e);
        } finally {
            JedisUtil.close(jedis);
        }
        return jedisPool;
    }

    private static String host(String address) {
        int i = address.indexOf(':');
        String host;
        if (i > 0) {
            host = address.substring(0, i);
        } else {
            host = address;
        }
        return host;
    }

    private static int port(String address) {
        int i = address.indexOf(':');
        int port;
        if (i > 0) {
            port = Integer.parseInt(address.substring(i + 1));
        } else {
            port = DEFAULT_REDIS_PORT;
        }
        return port;
    }
}