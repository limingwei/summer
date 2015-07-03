package cn.limw.summer.mysql.exception;

/**
 * @author li
 * @version 1 (2015年1月26日 下午3:53:10)
 * @since Java7
 */
public class DuplicateEntryRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 456149489426782629L;

    public DuplicateEntryRuntimeException(Throwable cause) {
        super(cause);
    }

    public DuplicateEntryRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}