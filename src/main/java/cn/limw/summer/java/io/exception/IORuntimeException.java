package cn.limw.summer.java.io.exception;

import java.io.IOException;

/**
 * @author li
 * @version 1 (2015年3月12日 下午5:11:08)
 * @since Java7
 */
public class IORuntimeException extends RuntimeException {
    private static final long serialVersionUID = 4921230645558083483L;

    public IORuntimeException(String message) {
        super(message);
    }

    public IORuntimeException(IOException cause) {
        super(cause);
    }
}