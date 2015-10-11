package summer.util;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:14:51)
 * @since Java7
 */
public class Assert {
    public static <T> T noNull(T value, String message) {
        if (null == value) {
            throw new RuntimeException(message);
        } else {
            return value;
        }
    }

    public static String noEmpty(String value, String message) {
        if (null == value || value.isEmpty()) {
            throw new RuntimeException(message);
        } else {
            return value;
        }
    }
}