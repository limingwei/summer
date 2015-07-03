package cn.limw.summer.log4j.layout.pattern;

import cn.limw.summer.log4j.parser.PatternParser;

/**
 * @author li
 * @version 1 (2015年6月30日 上午9:06:09)
 * @since Java7
 */
public class PatternLayout extends AbstractPatternLayout {
    protected PatternParser createPatternParser(String pattern) {
        return new PatternParser(pattern);
    }
}