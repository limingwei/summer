package summer.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author li
 * @version 1 (2015年10月11日 上午11:30:10)
 * @since Java7
 */
public class ListUtil {
    public static <T> List<T> newList(T[] filters) {
        return null == filters ? null : new ArrayList<T>(Arrays.asList(filters));
    }
}