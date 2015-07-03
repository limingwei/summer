package cn.limw.summer.java.sql.exception;

/**
 * @author li
 * @version 1 (2014年12月24日 上午10:29:29)
 * @since Java7
 */
public class TimestampCanNotRepresentRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -2319039349248705145L;

    private String canNotRepresentTimestamp;

    public TimestampCanNotRepresentRuntimeException(String canNotRepresentTimestamp, Exception e) {
        super("Value '" + canNotRepresentTimestamp + "' can not be represented as java.sql.Timestamp", e);
        this.canNotRepresentTimestamp = canNotRepresentTimestamp;
    }

    public String getCanNotRepresentTimestamp() {
        return canNotRepresentTimestamp;
    }
}