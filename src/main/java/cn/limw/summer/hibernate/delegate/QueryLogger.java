package cn.limw.summer.hibernate.delegate;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年5月28日 下午12:55:56)
 * @since Java7
 */
public class QueryLogger {
    private static final Logger log = Logs.slf4j();

    public void log(Long millis, String format, Object... arguments) {
        if (millis > 250) {
            log.error(format, arguments);
        } else if (millis > 100) {
            log.warn(format, arguments);
        } else if (millis > 50) {
            log.info(format, arguments);
        } else if (millis > 10) {
            log.debug(format, arguments);
        } else {
            log.trace(format, arguments);
        }
    }
}