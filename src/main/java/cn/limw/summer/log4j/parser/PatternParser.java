package cn.limw.summer.log4j.parser;

import cn.limw.summer.log4j.converter.AnchorConverter;

/**
 * @author li
 * @version 1 (2015年6月30日 上午9:12:33)
 * @since Java7
 */
public class PatternParser extends AbstractPatternParser {
    public PatternParser(String pattern) {
        super(pattern);
    }

    protected void finalizeConverter(char c) {
        switch (c) {
        case 'a':
            addConverter(new AnchorConverter());
            break;
        default:
            super.finalizeConverter(c);
        }
    }
}