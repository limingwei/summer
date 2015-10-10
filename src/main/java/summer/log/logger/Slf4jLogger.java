package summer.log.logger;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import summer.log.Logger;

/**
 * @author li
 * @version 1 (2015年10月10日 下午3:58:03)
 * @since Java7
 */
public class Slf4jLogger implements Logger {
    private org.slf4j.Logger slf4jLogger;

    public Slf4jLogger(org.slf4j.Logger slf4jLogger) {
        this.slf4jLogger = slf4jLogger;
    }

    public void trace(String format, Object... arguments) {
        slf4jLogger.trace(format, arguments);
    }

    public void trace(Throwable throwable, String format, Object... arguments) {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        slf4jLogger.trace(formattingTuple.getMessage(), throwable);
    }

    public void debug(String format, Object... arguments) {
        slf4jLogger.debug(format, arguments);
    }

    public void debug(Throwable throwable, String format, Object... arguments) {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        slf4jLogger.debug(formattingTuple.getMessage(), throwable);
    }

    public void info(String format, Object... arguments) {
        slf4jLogger.info(format, arguments);
    }

    public void info(Throwable throwable, String format, Object... arguments) {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        slf4jLogger.info(formattingTuple.getMessage(), throwable);
    }

    public void warn(String format, Object... arguments) {
        slf4jLogger.warn(format, arguments);
    }

    public void warn(Throwable throwable, String format, Object... arguments) {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        slf4jLogger.warn(formattingTuple.getMessage(), throwable);
    }

    public void error(String format, Object... arguments) {
        slf4jLogger.error(format, arguments);
    }

    public void error(Throwable throwable, String format, Object... arguments) {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        slf4jLogger.error(formattingTuple.getMessage(), throwable);
    }
}