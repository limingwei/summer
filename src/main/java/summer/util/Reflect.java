package summer.util;

/**
 * @author li
 * @version 1 (2015年10月10日 下午1:01:49)
 * @since Java7
 */
public class Reflect {
    public static Class<?> classForName(String typeName) {
        try {
            return Class.forName(typeName);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T newInstance(Class<T> type) {
        try {
            return type.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}