package cn.limw.summer.util;

/**
 * @author li
 * @version 1 (2014年12月16日 下午2:42:04)
 * @since Java7
 */
public class BoolUtil {
    /**
     * true 1 为true
     */
    public static Boolean couldBeTrue(Object value) {
        if (null == value) {
            return false;
        } else if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue() > 0;
        } else if (value instanceof String) {
            String string = (String) value;
            return string.equalsIgnoreCase("true") || value.equals("1");
        } else {
            String string = value + "";
            return string.equalsIgnoreCase("true") || value.equals("1");
        }
    }

    public static Boolean isTrue(Object object) {
        return null != object && object instanceof Boolean && (Boolean) object;
    }

    public static Boolean isFalse(Boolean bool) {
        return null != bool && !bool;
    }

    public static Boolean nullOrTrue(Boolean bool) {
        return null == bool || bool;
    }

    public static Boolean falseWhenNull(Boolean bool) {
        return null == bool ? false : bool;
    }

    public static Boolean trueWhenNull(Boolean bool) {
        return null == bool ? true : bool;
    }

    public static Boolean toBool(Object value) {
        if (value == null) {
            return null;
        }
        if (Boolean.class.isInstance(value)) {
            return (Boolean) value;
        }
        if (Number.class.isInstance(value)) {
            final int intValue = ((Number) value).intValue();
            return intValue == 0 ? Boolean.FALSE : Boolean.TRUE;
        }
        if (value instanceof String) {
            return "true".equalsIgnoreCase((String) value);
        }
        throw new RuntimeException("value " + value + " can not convert to boolean");
    }
}