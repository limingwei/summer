package cn.limw.summer.javax.mail.exception;

import javax.mail.AuthenticationFailedException;

/**
 * @author li
 * @version 1 (2015年5月8日 下午1:30:07)
 * @since Java7
 * @see javax.mail.AuthenticationFailedException
 */
public class AuthenticationFailedRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 5641431828049547223L;

    public AuthenticationFailedRuntimeException(String message, AuthenticationFailedException e) {
        super(message, e);
    }
}