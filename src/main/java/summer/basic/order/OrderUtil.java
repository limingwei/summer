package summer.basic.order;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author li
 * @version 1 (2015年10月14日 下午9:42:49)
 * @since Java7
 */
public class OrderUtil {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T[] sort(T[] array) {
        Arrays.sort(array, new Comparator() {
            public int compare(Object o1, Object o2) {
                long deviation = (long) getOrder(o1) - (long) getOrder(o2);
                return deviation > 0 ? 1 : (deviation < 0 ? -1 : 0);
            }
        });
        return array;
    }

    public static int getOrder(Object object) {
        if (null == object) {
            return 0;
        } else if (object instanceof Ordered) {
            return ((Ordered) object).getOrder();
        } else {
            Order order = object.getClass().getAnnotation(Order.class);
            if (null == order) {
                return 0;
            } else {
                return order.value();
            }
        }
    }
}