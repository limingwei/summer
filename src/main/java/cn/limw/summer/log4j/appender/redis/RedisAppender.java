package cn.limw.summer.log4j.appender.redis;

import java.util.Map;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年7月3日 上午9:09:12)
 * @since Java7
 * @see cn.limw.summer.log4j.appender.jdbc.JdbcAppender
 */
public class RedisAppender extends AppenderSkeleton implements Appender {
    private Jedis jedis;

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public Jedis getJedis() {
        if (null == jedis) {
            jedis = RedisAppenderUtil.initJedis(url);
        }
        return jedis;
    }

    public void close() {
        jedis.close();
    }

    public boolean requiresLayout() {
        return true;
    }

    protected void append(LoggingEvent event) {
        Map<String, String> map = RedisAppenderUtil.formatLoggingEvent(event, getLayout());

        Pipeline pipeline = getJedis().pipelined();
        pipeline.hmset(StringUtil.uuid(), map);
    }
}