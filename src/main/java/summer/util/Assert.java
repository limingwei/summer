package summer.util;


/**
 * @author li
 * @version 1 (2015年10月9日 下午3:14:51)
 * @since Java7
 */
public class Assert {
    public static <T> T noNull(T target, String message) {
        if (null == target) {
            throw new RuntimeException(message);
        } else {
            return target;
        }
    }
}