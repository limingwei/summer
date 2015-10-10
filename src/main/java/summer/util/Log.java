package summer.util;

import org.slf4j.LoggerFactory;

import summer.log.Logger;

/**
 * @author li
 * @version 1 (2015年10月10日 下午1:46:04)
 * @since Java7
 */
public class Log {
    public static Logger get() {
        LoggerFactory.getLogger("");
        return null;
    }
}