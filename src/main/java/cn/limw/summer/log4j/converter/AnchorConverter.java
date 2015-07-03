package cn.limw.summer.log4j.converter;

import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.spi.LoggingEvent;

import cn.limw.summer.slf4j.logger.AnchorLogger;

/**
 * @author li
 * @version 1 (2015年6月30日 上午9:17:52)
 * @since Java7
 */
public class AnchorConverter extends PatternConverter {
    protected String convert(LoggingEvent event) {
        return AnchorLogger.getLoggerAnchor();
    }
}