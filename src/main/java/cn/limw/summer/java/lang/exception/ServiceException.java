package cn.limw.summer.java.lang.exception;

/**
 * 业务异常基类
 * @author li
 * @version 1 (2015年1月23日 下午5:23:47)
 * @since Java7
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -4336080814120716884L;

    public ServiceException(String message) {
        super(message);
    }
}