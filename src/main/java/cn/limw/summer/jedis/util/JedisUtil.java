package cn.limw.summer.jedis.util;

import redis.clients.jedis.Jedis;

/**
 * @author li
 * @version 1 (2015年3月27日 下午4:05:02)
 * @since Java7
 */
public class JedisUtil {
    public static void close(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
        }
    }
}