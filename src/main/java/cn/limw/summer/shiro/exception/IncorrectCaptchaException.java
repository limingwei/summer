package cn.limw.summer.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author lgb
 * @version 1 (2014年8月28日下午3:00:18)
 * @since Java7
 */
public class IncorrectCaptchaException extends AuthenticationException {
    private static final long serialVersionUID = -2706778431558994611L;

    public IncorrectCaptchaException() {
        super();
    }

    public IncorrectCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCaptchaException(String message) {
        super(message);
    }

    public IncorrectCaptchaException(Throwable cause) {
        super(cause);
    }
}