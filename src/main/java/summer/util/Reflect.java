package summer.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author li
 * @version 1 (2015年10月10日 下午1:01:49)
 * @since Java7
 */
public class Reflect {
    public static Type[] getGenericSuperclassActualTypeArguments(Class<?> type) {
        Type genericSuperclass = type.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            return parameterizedType.getActualTypeArguments();
        }
        throw new RuntimeException("type=" + type + ", type.getGenericSuperclass()=" + genericSuperclass + ", is not ParameterizedType");
    }

    public static Type[] getGenericInterfacesActualTypeArguments(Class<?> type, Class<?> genericInterfaceType) {
        Type[] genericInterfaces = type.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                if (genericInterfaceType.equals(parameterizedType.getRawType())) {
                    return parameterizedType.getActualTypeArguments();
                }
            }
        }
        throw new RuntimeException("ParameterizedType not found, type=" + type + ", genericInterfaceType=" + genericInterfaceType);
    }

    public static String typeToJavaCode(Class<?> type) {
        if (type.isArray()) {
            return type.getComponentType().getName() + "[]";
        } else {
            return type.getName();
        }
    }

    public static List<Method> getPublicMethods(Class<?> originalType) {
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

    public static Class<?> classForName(String typeName) {
        try {
            return Class.forName(typeName);
        } catch (ClassNotFoundException e) {
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

    public static Object invokeMethod(Object target, Method method, Object[] args) {
        try {
            method.setAccessible(true);
            return method.invoke(target, args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static Field getDeclaredField(Class<?> targetType, String name) {
        try {
            return targetType.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(targetType + " do not has field " + name, e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Field> getDeclaredFields(Class<?> type) {
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        Class<?> superType = type.getSuperclass();
        if (!Object.class.equals(superType)) {
            fields.addAll(getDeclaredFields(superType));
        }
        return fields;
    }

    public static Boolean isPrimitiveType(Class<?> type) {
        Assert.noNull(type, "type is null");
        return type.isPrimitive() //
                || type.equals(String.class) || type.equals(Boolean.class) || type.equals(Character.class) || type.equals(Byte.class) //
                || Number.class.isAssignableFrom(type);
    }
}