package cn.limw.summer.util;

/**
 * @author li ( limingwei@mail.com )
 * @version 1 ( 2014年5月16日 下午4:58:38 )
 */
public class Nums {
    public static Integer toInt(Object value) {
        return toInt(value, null);
    }

    public static Integer toInt(Object value, Integer defaultValue) {
        if (null == value) {
            return defaultValue;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return defaultValue;
            } else {
                return new Integer((String) value);
            }
        } else {
            throw new IllegalArgumentException("must input number or string");
        }
    }

    public static Long toLong(Object value) {
        return null == value ? null : (value instanceof Number ? ((Number) value).longValue() : new Long(value.toString()));
    }

    public static Long[] toLongArray(String longArray) {
        if (StringUtil.isEmpty(longArray)) {
            return new Long[0];
        }
        String[] strings = longArray.split(",");
        Long[] longs = new Long[strings.length];
        for (int i = 0; i < strings.length; i++) {
            longs[i] = toLong(strings[i]);
        }
        return longs;
    }

    public static Integer[] toIntArray(String intArray) {
        if (StringUtil.isEmpty(intArray)) {
            return new Integer[0];
        }
        String[] strings = intArray.split(",");
        Integer[] ints = new Integer[strings.length];
        for (int i = 0; i < strings.length; i++) {
            ints[i] = toInt(strings[i]);
        }
        return ints;
    }

    public static Boolean equals(Integer num1, Integer num2) {
        return null != num1 && null != num2 && num1.intValue() == num2.intValue();
    }

    public static Long toLong(Object value, Long defaultValue) {
        if (null == value) {
            return defaultValue;
        } else if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return defaultValue;
            } else {
                return new Long((String) value);
            }
        } else {
            throw new IllegalArgumentException("must input number or string");
        }
    }
}