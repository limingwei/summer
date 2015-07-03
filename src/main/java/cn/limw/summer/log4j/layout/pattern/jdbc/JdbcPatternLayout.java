package cn.limw.summer.log4j.layout.pattern.jdbc;

import cn.limw.summer.log4j.layout.pattern.PatternLayout;

/**
 * @author li
 * @version 1 (2015年6月30日 下午4:07:30)
 * @since Java7
 */
public class JdbcPatternLayout extends PatternLayout {
    protected JdbcPatternParser createPatternParser(String pattern) {
        return new JdbcPatternParser(pattern);
    }
}