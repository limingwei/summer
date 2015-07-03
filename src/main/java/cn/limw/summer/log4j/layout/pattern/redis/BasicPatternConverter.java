package cn.limw.summer.log4j.layout.pattern.redis;

import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.spi.LoggingEvent;

import cn.limw.summer.log4j.layout.pattern.converter.PatternConverterConstants;

/**
 * @author li
 * @version 1 (2015年6月30日 下午4:14:49)
 * @since Java7
 * @see org.apache.log4j.helpers.PatternParser
 */
public class BasicPatternConverter extends PatternConverter implements PatternConverterConstants {
    private int type;

    public BasicPatternConverter(FormattingInfo formattingInfo, int type) {
        super(formattingInfo);
        this.type = type;
    }

    public String convert(LoggingEvent event) {
        switch (type) {
        case RELATIVE_TIME_CONVERTER:
            return (Long.toString(event.timeStamp - LoggingEvent.getStartTime()));
        case THREAD_CONVERTER:
            return event.getThreadName();
        case LEVEL_CONVERTER:
            return event.getLevel().toString();
        case NDC_CONVERTER:
            return event.getNDC();
        case MESSAGE_CONVERTER:
            return escapeQuote(event.getRenderedMessage());
        default:
            return null;
        }
    }

    private String escapeQuote(String renderedMessage) {
        return renderedMessage.replace('\'', '_').replace('"', ' ').replace('\\', ' ');
    }
}