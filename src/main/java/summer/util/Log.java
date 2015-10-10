package summer.util;

import summer.log.Logger;
import summer.log.logger.Slf4jLogger;

/**
 * @author li
 * @version 1 (2015年10月10日 下午1:46:04)
 * @since Java7
 */
public class Log {
    public static Logger slf4j(String name) {
        return new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(name));
    }

    public static Logger slf4j(Class<?> type) {
        return slf4j(type.getName());
    }

    public static Logger slf4j() {
        return slf4j(Thread.currentThread().getStackTrace()[2].getClassName());
    }
}