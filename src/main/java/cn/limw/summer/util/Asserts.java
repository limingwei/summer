package cn.limw.summer.util;

import java.util.Map;

/**
 * @author 明伟
 * @version 1 (2014年6月14日 上午10:41:04)
 * @since Java7
 */
public class Asserts {
    public static <T> T noNull(T value) {
        return noNull(value, "value must be not null");
    }

    public static <T> T noNull(T value, RuntimeException exception) {
        if (null == value) {
            throw exception;
        }
        return value;
    }

    public static String noEmpty(String value) {
        return noEmpty(value, "string must be not empty");
    }

    public static String noEmpty(String value, String errorMessage) {
        if (null == value || value.trim().isEmpty()) {
            throw new AssertException(errorMessage);
        }
        return value;
    }

    public static <T> T[] noEmpty(T[] values) {
        return noEmpty(values, "array must not be null or empty");
    }

    public static <T> T[] noEmpty(T[] values, String errorMessage) {
        if (null == values || values.length < 1) {
            throw new AssertException(errorMessage);
        }
        return values;
    }

    public static void equals(Object expected, Object actual, String message) {
        if (null != expected && null != actual && expected.equals(actual)) {
            //
        } else {
            throw new AssertException(message);
        }
    }

    public static Boolean isTrue(Boolean expectedTrue, String message) {
        return isTrue(expectedTrue, new AssertException(message));
    }

    public static Boolean isTrue(Boolean expectedTrue, RuntimeException exception) {
        if (null != expectedTrue && expectedTrue) {
            return expectedTrue;
        } else {
            throw exception;
        }
    }

    public static Boolean isTrue(Boolean expectedTrue) {
        return isTrue(expectedTrue, "value must be true");
    }

    /**
     * @author li
     * @version 1 (2014年12月17日 下午4:51:43)
     * @since Java7
     */
    public static class AssertException extends RuntimeException {
        private static final long serialVersionUID = 2739206021669941835L;

        public AssertException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static Boolean isFalse(Boolean expectedFalse, String message) {
        return isTrue(!expectedFalse, message);
    }

    public static <T> T noNull(T value, String errorMessage) {
        if (null == value) {
            throw new AssertException(errorMessage);
        }
        return value;
    }

    public static void isNull(Object value, String errorMessage) {
        if (null != value) {
            throw new AssertException(errorMessage);
        }
    }

    public static void noEmpty(Map map, String errorMessage) {
        if (null == map || map.isEmpty()) {
            throw new AssertException(errorMessage);
        }
    }

    /**
     * 传入的字符串不全都是空的
     * @return
     */
    public static String[] noAllEmpty(String... strs) {
        if (null == strs || strs.length < 1) {
            throw new AssertException("asserting must not be all empty string, but input nothing");
        }
        for (String each : strs) {
            if (!StringUtil.isEmpty(each)) {
                return strs;
            }
        }
        throw new AssertException("asserting must not be all empty string, but all empty");
    }

    /**
     * 传入的字符串全都不是空的
     */
    public static String[] noAnyEmpty(String[] array, String errorMessage) {
        if (null == array || array.length < 1) {
            throw new AssertException(errorMessage);
        }
        for (String each : array) {
            if (StringUtil.isEmpty(each)) {
                throw new AssertException(errorMessage);
            }
        }
        return array;
    }

    /**
     * 传入的字符串全都不是空的
     */
    public static String[] noAnyEmpty(String[] array) {
        if (null == array || array.length < 1) {
            throw new AssertException("asserting must not be all empty string, but input nothing");
        }
        for (String each : array) {
            if (StringUtil.isEmpty(each)) {
                throw new AssertException("asserting must not be any empty string, but empty");
            }
        }
        return array;
    }
}