package summer.util;

import java.util.List;

/**
 * @author li
 * @version 1 (2015年10月11日 下午2:38:12)
 * @since Java7
 */
public class StringUtil {
    public static String join(Object[] array, String linker) {
        StringBuilder stringBuilder = new StringBuilder();
        if (null == array || 0 == array.length) {
            return stringBuilder.toString();
        }
        stringBuilder.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            stringBuilder.append(linker).append(array[i]);
        }
        return stringBuilder.toString();
    }

    @SuppressWarnings("rawtypes")
    public static String join(List list, String linker) {
        return join(list.toArray(), linker);
    }
}