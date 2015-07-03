package cn.limw.summer.java.io.exception;

import java.io.FileNotFoundException;

/**
 * @author li
 * @version 1 (2014年11月25日 下午4:48:51)
 * @since Java7
 */
public class FileNotFoundRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -7564247224572731008L;

    public FileNotFoundRuntimeException(FileNotFoundException cause) {
        super(cause);
    }
}