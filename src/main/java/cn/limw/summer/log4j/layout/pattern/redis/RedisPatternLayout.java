package cn.limw.summer.log4j.layout.pattern.redis;

import cn.limw.summer.log4j.layout.pattern.PatternLayout;

/**
 * @author li
 * @version 1 (2015年7月3日 上午10:27:54)
 * @since Java7
 * @see cn.limw.summer.log4j.layout.pattern.jdbc.JdbcPatternLayout
 */
public class RedisPatternLayout extends PatternLayout {
    protected RedisPatternParser createPatternParser(String pattern) {
        return new RedisPatternParser(pattern);
    }
}