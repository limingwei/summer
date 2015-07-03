package cn.limw.summer.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author li
 * @version 1 (2014年11月20日 下午5:24:31)
 * @since Java7
 */
public class SetUtil {
    public static <T> Set<T> asSet(T... items) {
        Set<T> set = new HashSet<T>();
        for (T each : items) {
            set.add(each);
        }
        return set;
    }

    /**
     * 判断集合是否包含指定元素, 集合为空时返回false, 指定元素为空时返回 falseS
     */
    public static <T> Boolean contains(Set<T> set, T value) {
        return null != set && null != value && set.contains(value);
    }

    public static <T> Set<T> noNull(Set<T> set) {
        return null == set ? new HashSet<T>() : set;
    }

    public static Boolean isEmpty(Set<?> set) {
        return null == set || set.isEmpty();
    }

    public static Integer size(Set<?> set) {
        if (null == set) {
            return -1;
        } else {
            return set.size();
        }
    }

    public static <T> T firstOrNull(Set<T> set) {
        if (null == set || set.size() < 1) {
            return null;
        } else {
            return set.iterator().next();
        }
    }
}