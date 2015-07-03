package cn.limw.summer.slf4j.logger;

import org.slf4j.Logger;

import cn.limw.summer.slf4j.wrapper.LoggerWrapper;
import cn.limw.summer.util.StringUtil;

/**
 * 为每一条日志添加一个锚,通过ThreadLocal传递或者通过DubboFilter传递
 * @author li
 * @version 1 (2015年6月29日 上午10:50:35)
 * @since Java7
 * @see cn.limw.summer.dubbo.filter.anchor.ProviderSideLoggerAnchorFilter
 * @see cn.limw.summer.dubbo.filter.anchor.ConsumerSideLoggerAnchorFilter
 */
public class AnchorLogger extends LoggerWrapper {
    private static final ThreadLocal<String> ANCHOR_THREAD_LOCAL = new ThreadLocal<String>();

    public AnchorLogger(Logger logger) {
        super(logger);
    }

    public static String getLoggerAnchor() {
        String loggerAnchor = ANCHOR_THREAD_LOCAL.get();
        if (StringUtil.isEmpty(loggerAnchor)) {
            loggerAnchor = doGetLoggerAnchor();
        }
        return loggerAnchor;
    }

    private synchronized static final String doGetLoggerAnchor() {
        String loggerAnchor = ANCHOR_THREAD_LOCAL.get();
        if (StringUtil.isEmpty(loggerAnchor)) {
            String uuid = StringUtil.uuid();
            setLoggerAnchor(uuid);
            return uuid;
        } else {
            return loggerAnchor;
        }
    }

    public synchronized static void setLoggerAnchor(String value) {
        ANCHOR_THREAD_LOCAL.set(value);
    }

    private String withAnchor(String format) {
        return "[#Anchor " + getLoggerAnchor() + "]\t" + format;
    }

    public void info(String format, Object arg) {
        super.info(withAnchor(format), arg);
    }

    public void info(String format, Object arg1, Object arg2) {
        super.info(withAnchor(format), arg1, arg2);
    }

    public void info(String format, Object... arguments) {
        super.info(withAnchor(format), arguments);
    }

    public void info(String msg) {
        super.info(withAnchor(msg));
    }

    public void info(String msg, Throwable t) {
        super.info(withAnchor(msg), t);
    }

    public void error(String format, Object arg) {
        super.error(withAnchor(format), arg);
    }

    public void error(String format, Object arg1, Object arg2) {
        super.error(withAnchor(format), arg1, arg2);
    }

    public void error(String format, Object... arguments) {
        super.error(withAnchor(format), arguments);
    }

    public void error(String msg) {
        super.error(withAnchor(msg));
    }

    public void error(String msg, Throwable t) {
        super.error(withAnchor(msg), t);
    }

    public void warn(String format, Object arg) {
        super.warn(withAnchor(format), arg);
    }

    public void warn(String format, Object arg1, Object arg2) {
        super.warn(withAnchor(format), arg1, arg2);
    }

    public void warn(String format, Object... arguments) {
        super.warn(withAnchor(format), arguments);
    }

    public void warn(String msg) {
        super.warn(withAnchor(msg));
    }

    public void warn(String msg, Throwable t) {
        super.warn(withAnchor(msg), t);
    }

    public void debug(String format, Object arg) {
        super.debug(withAnchor(format), arg);
    }

    public void debug(String format, Object arg1, Object arg2) {
        super.debug(withAnchor(format), arg1, arg2);
    }

    public void debug(String msg, Throwable t) {
        super.debug(withAnchor(msg), t);
    }

    public void debug(String msg) {
        super.debug(withAnchor(msg));
    }

    public void debug(String format, Object... arguments) {
        super.debug(withAnchor(format), arguments);
    }
}