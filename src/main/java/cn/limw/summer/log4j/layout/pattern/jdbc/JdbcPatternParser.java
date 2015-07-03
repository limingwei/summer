package cn.limw.summer.log4j.layout.pattern.jdbc;

import org.apache.log4j.helpers.PatternConverter;

import cn.limw.summer.log4j.layout.pattern.converter.PatternConverterConstants;
import cn.limw.summer.log4j.parser.PatternParser;

/**
 * @author li
 * @version 1 (2015年6月30日 下午4:08:41)
 * @since Java7
 */
public class JdbcPatternParser extends PatternParser {
    public JdbcPatternParser(String pattern) {
        super(pattern);
    }

    protected void finalizeConverter(char c) {
        switch (c) {
        case 'm':
            PatternConverter pc = new BasicPatternConverter(formattingInfo, PatternConverterConstants.MESSAGE_CONVERTER);
            currentLiteral.setLength(0);
            addConverter(pc);
            break;
        default:
            super.finalizeConverter(c);
        }
    }
}