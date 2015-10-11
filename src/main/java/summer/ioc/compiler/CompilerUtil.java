package summer.ioc.compiler;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author li
 * @version 1 (2015年10月11日 上午10:08:16)
 * @since Java7
 */
public class CompilerUtil {
    public static List<Method> getOriginalPublicMethods(Class<?> originalType) {
        List<Method> list = new ArrayList<Method>();
        Method[] methods = originalType.getMethods();
        for (Method method : methods) {
            if (!method.getDeclaringClass().equals(Object.class) //
                    && !Modifier.isStatic(method.getModifiers())//
                    && !Modifier.isFinal(method.getModifiers())) {
                list.add(method);
            }
        }
        return list;
    }
}