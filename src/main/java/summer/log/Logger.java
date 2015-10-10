package summer.log;

/**
 * @author li
 * @version 1 (2015年10月10日 下午3:52:25)
 * @since Java7
 */
public interface Logger {
    public void trace(String format, Object... arguments);

    public void trace(Throwable throwable, String format, Object... arguments);

    public void debug(String format, Object... arguments);

    public void debug(Throwable throwable, String format, Object... arguments);

    public void info(String format, Object... arguments);

    public void info(Throwable throwable, String format, Object... arguments);

    public void warn(String format, Object... arguments);

    public void warn(Throwable throwable, String format, Object... arguments);

    public void error(String format, Object... arguments);

    public void error(Throwable throwable, String format, Object... arguments);
}