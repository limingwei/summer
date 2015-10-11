package summer.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    public static void setFieldValue(Object target, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static Field getField(Class<?> targetType, String name) {
        try {
            return targetType.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(targetType + " do not has field " + name, e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invokeMethod(Object target, Method method, Object[] args) {
        try {
            method.setAccessible(true);
            return method.invoke(target, args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}