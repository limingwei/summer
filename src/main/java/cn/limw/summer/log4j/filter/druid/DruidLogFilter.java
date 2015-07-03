package cn.limw.summer.log4j.filter.druid;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author li
 * @version 1 (2014年10月14日 下午1:59:57)
 * @since Java7
 */
public class DruidLogFilter extends Filter {
    public int decide(LoggingEvent event) {
        if (event.getMessage().toString().contains("executed.") && !event.getMessage().toString().contains("query executed.")) {
            return ACCEPT;
        } else {
            return DENY;
        }
    }
}