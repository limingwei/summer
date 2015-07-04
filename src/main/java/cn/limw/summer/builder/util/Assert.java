package cn.limw.summer.builder.util;

import java.util.Collection;

/**
 * Assert
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年2月27日 下午4:43:07)
 */
public class Assert {
    /**
     * 返回非空的value或者抛异常
     */
    public static <T> T noNull(T value, String errorMessage) {
        if (null == value) {
            throw Errors.wrap(errorMessage);
        } else {
            return value;
        }
    }

    /**
     * 返回非空的集合或者抛异常
     */
    public static <T extends Collection<?>> T noEmpty(T collection, String errorMessage) {
        if (null == collection || collection.isEmpty()) {
            throw Errors.wrap(errorMessage);
        } else {
            return collection;
        }
    }
}