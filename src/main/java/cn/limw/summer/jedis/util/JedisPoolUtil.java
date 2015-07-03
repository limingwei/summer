package cn.limw.summer.jedis.util;

import redis.clients.jedis.JedisPool;

/**
 * @author li
 * @version 1 (2015年3月27日 下午3:39:46)
 * @since Java7
 */
public class JedisPoolUtil {
    public static void close(JedisPool jedisPool) {
        if (null != jedisPool) {
            jedisPool.close();
        }
    }
}