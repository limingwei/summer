package cn.limw.summer.builder.util;

/**
 * Errors
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年1月8日 下午6:04:19)
 */
public class Errors {
    public static RuntimeException wrap(Throwable e) {
        return new RuntimeException(e);
    }

    public static RuntimeException wrap(String message) {
        return new RuntimeException(message);
    }

    public static RuntimeException wrap(String message, Throwable e) {
        return new RuntimeException(message, e);
    }
}