package cn.limw.summer.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li
 * @version 1 (2014年7月7日 下午2:20:40)
 * @since Java7
 */
public class ListUtil {
    public static <T> List<T> newList() {
        return new ArrayList<T>();
    }

    public static <T> List<T> concat(List<T>... lists) {
        List<T> list = new ArrayList<T>();
        for (List<T> each : lists) {
            list.addAll(each);
        }
        return list;
    }

    public static Boolean isEmpty(List<?> list) {
        return null == list || list.isEmpty();
    }

    public static <T> List<T> asList(T... items) {
        List<T> list = new ArrayList<T>();
        for (T each : items) {
            if (null != each) {
                list.add(each);
            }
        }
        return list;
    }

    public static <T> T first(List<T> list) {
        return (null == list || list.isEmpty()) ? null : list.get(0);
    }

    public static <T> List<T> subList(List<T> list, Integer fromIndex, Integer toIndex) {
        if (toIndex > list.size()) {
            toIndex = list.size();
        }
        return list.subList(fromIndex, toIndex);
    }

    public static Integer size(List<?> list) {
        if (null == list) {
            return -1;
        } else {
            return list.size();
        }
    }

    public static <T> List<T> add(List<T> list, T item) {
        if (null == list) {
            list = new ArrayList<T>();
        }
        list.add(item);
        return list;
    }
}