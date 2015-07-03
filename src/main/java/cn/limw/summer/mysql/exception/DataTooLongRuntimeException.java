package cn.limw.summer.mysql.exception;

/**
 * @author li
 * @version 1 (2015年2月2日 下午3:56:52)
 * @since Java7
 */
public class DataTooLongRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -2739218494775854887L;

    public DataTooLongRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}