package summer.util;

/**
 * @author li
 * @version 1 (2015年10月15日 下午1:04:03)
 * @since Java7
 */
public class PrimitiveTypeUtil {
    public static byte toPrimitive(Byte value) {
        return null == value ? 0 : value.byteValue();
    }

    public static short toPrimitive(Short value) {
        return null == value ? 0 : value.shortValue();
    }

    public static int toPrimitive(Integer value) {
        return null == value ? 0 : value.intValue();
    }

    public static long toPrimitive(Long value) {
        return null == value ? 0 : value.longValue();
    }

    public static double toPrimitive(Double value) {
        return null == value ? 0 : value.doubleValue();
    }

    public static float toPrimitive(Float value) {
        return null == value ? 0 : value.floatValue();
    }

    public static char toPrimitive(Character value) {
        return null == value ? 0 : value.charValue();
    }

    public static boolean toPrimitive(Boolean value) {
        return null == value ? false : value.booleanValue();
    }
}