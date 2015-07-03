package cn.limw.summer.log4j.appender.redis;

import java.util.Map;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Jsons;
import cn.limw.summer.util.Nums;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年7月3日 上午11:57:16)
 * @since Java7
 */
@SuppressWarnings("unchecked")
public class RedisAppenderUtil {
    /**
     * 127.0.0.1:6379?password=123
     */
    public static synchronized Jedis initJedis(String url) {
        Asserts.noEmpty(url, "参数url不可以为空");

        Jedis jedis = new Jedis(getHostInUrl(url), getPortInUrl(url));
        String password = getPasswordInUrl(url);
        if (!StringUtil.isEmpty(password)) {
            jedis.auth(password);
        }
        return jedis;
    }

    private static String getPasswordInUrl(String url) {
        String pwdStr = "?password=";
        int pwdIndex = url.indexOf(pwdStr);
        if (pwdIndex > 0) {
            return url.substring(pwdIndex + pwdStr.length());
        }
        return "";
    }

    private static Integer getPortInUrl(String url) {
        int index = url.indexOf(':');
        if (index > 0) {
            return Nums.toInt(url.substring(index + 1).replace("?password=" + getPasswordInUrl(url), ""));
        }
        return Protocol.DEFAULT_PORT;
    }

    private static String getHostInUrl(String url) {
        int index = url.indexOf(':');
        if (index > 0) {
            return url.substring(0, index);
        } else {
            return url;
        }
    }

    public static Map<String, String> formatLoggingEvent(LoggingEvent event, Layout layout) {
        String string = layout.format(event).replace('\'', '"');
        return Jsons.toMap(string);
    }
}