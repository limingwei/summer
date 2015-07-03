package cn.limw.summer.slf4j.logger;

import org.slf4j.Logger;

import cn.limw.summer.slf4j.LogLevel;
import cn.limw.summer.slf4j.wrapper.LoggerWrapper;

/**
 * @author li
 * @version 1 (2015年6月3日 下午5:15:12)
 * @since Java7
 */
public class Slf4jLogger extends LoggerWrapper {
    public Slf4jLogger(Logger logger) {
        super(logger);
    }

    public void log(String level, String msg) {
        if (LogLevel.ERROR.equalsIgnoreCase(level)) {
            super.error(msg);
        } else if (LogLevel.WARN.equalsIgnoreCase(level)) {
            super.warn(msg);
        } else if (LogLevel.INFO.equalsIgnoreCase(level)) {
            super.info(msg);
        } else if (LogLevel.DEBUG.equalsIgnoreCase(level)) {
            super.debug(msg);
        } else if (LogLevel.TRACE.equalsIgnoreCase(level)) {
            super.trace(msg);
        } else {
            throw new RuntimeException("error level '" + level + "' is not in {error,warn,info,debug,trace}");
        }
    }

    public void log(String level, String format, Object... arguments) {
        if (LogLevel.ERROR.equalsIgnoreCase(level)) {
            super.error(format, arguments);
        } else if (LogLevel.WARN.equalsIgnoreCase(level)) {
            super.warn(format, arguments);
        } else if (LogLevel.INFO.equalsIgnoreCase(level)) {
            super.info(format, arguments);
        } else if (LogLevel.DEBUG.equalsIgnoreCase(level)) {
            super.debug(format, arguments);
        } else if (LogLevel.TRACE.equalsIgnoreCase(level)) {
            super.trace(format, arguments);
        } else {
            throw new RuntimeException("error level '" + level + "' is not in {error,warn,info,debug,trace}");
        }
    }

    public void log(String level, String msg, Throwable t) {
        if (LogLevel.ERROR.equalsIgnoreCase(level)) {
            super.error(msg, t);
        } else if (LogLevel.WARN.equalsIgnoreCase(level)) {
            super.warn(msg, t);
        } else if (LogLevel.INFO.equalsIgnoreCase(level)) {
            super.info(msg, t);
        } else if (LogLevel.DEBUG.equalsIgnoreCase(level)) {
            super.debug(msg, t);
        } else if (LogLevel.TRACE.equalsIgnoreCase(level)) {
            super.trace(msg, t);
        } else {
            throw new RuntimeException("error level '" + level + "' is not in {error,warn,info,debug,trace}");
        }
    }
}