package summer.ioc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author li
 * @version 1 (2015年10月11日 下午2:30:15)
 * @since Java7
 */
public class MethodPool {
    private static final Map<String, Method> METHOD_MAP = new HashMap<String, Method>();

    public static void putMethod(String methodSignature, Method method) {
        METHOD_MAP.put(methodSignature, method);
    }

    public static Method getMethod(String methodSignature) {
        return METHOD_MAP.get(methodSignature);
    }
}