package cn.limw.summer.spring.web.interceptor.signcheck;

/**
 * @author li
 * @version 1 (2015年5月13日 上午8:56:14)
 * @since Java7
 */
public class SignCheckErrorException extends RuntimeException {
    private static final long serialVersionUID = 4350707148063115136L;

    public SignCheckErrorException(String message) {
        super(message);
    }
}