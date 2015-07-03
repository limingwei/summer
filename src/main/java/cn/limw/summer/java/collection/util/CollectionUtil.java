package cn.limw.summer.java.collection.util;

import java.util.Collection;

/**
 * @author li
 * @version 1 (2014年12月5日 下午3:18:07)
 * @since Java7
 */
public class CollectionUtil {
    /**
     * 返回集合的最后一个元素
     */
    public static Object last(Collection collection) {
        return collection.toArray()[collection.size() - 1];
    }

    /**
     * 返回集合的第一个元素
     */
    public static Object first(Collection collection) {
        return collection.toArray()[0];
    }
}