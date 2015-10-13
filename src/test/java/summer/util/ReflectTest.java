package summer.util;

/**
 * @author li
 * @version 1 (2015年10月13日 下午4:08:53)
 * @since Java7
 */
public class ReflectTest {
    public static void main(String[] args) {
        System.err.println(Reflect.isPrimitiveType(int.class));
        System.err.println(Reflect.isPrimitiveType(Integer.class));
    }
}