package cn.limw.summer.util;

import cn.limw.summer.slf4j.logger.Slf4jLogger;

/**
 * @author 明伟
 * @version 1 (2014年6月14日 上午10:32:40)
 * @since Java7
 */
public class Logs {
    public static org.slf4j.Logger slf4j(String name) {
        return new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(name));
    }

    public static org.slf4j.Logger slf4j(Class<?> type) {
        return slf4j(type.getName());
    }

    public static org.slf4j.Logger slf4j() {
        return slf4j(Thread.currentThread().getStackTrace()[2].getClassName());
    }

    public static org.apache.log4j.Logger log4j(String name) {
        return org.apache.log4j.Logger.getLogger(name);
    }

    public static org.apache.log4j.Logger log4j(Class<?> type) {
        return log4j(type.getName());
    }

    public static org.apache.log4j.Logger log4j() {
        return log4j(Thread.currentThread().getStackTrace()[2].getClassName());
    }
}