package cn.limw.summer.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.limw.summer.java.collection.NiceToStringList;

/**
 * @author li ( limingwei@mail.com )
 * @version 1 ( 2014年5月22日 下午12:35:36 )
 */
public class Mirrors {
    public static Class<?> classForName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Class<?>> classForName(String... types) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (String type : types) {
            classes.add(classForName(type));
        }
        return classes;
    }

    /**
     * 可能还不健全
     */
    public static Boolean isPrimitiveTypes(Class<?> type) {
        return type.isPrimitive() || String.class.equals(type) || Boolean.class.equals(type) || Character.class.equals(type) || Number.class.isAssignableFrom(type);
    }

    public static Boolean isBasicType(Class<?> type) {
        return type.isPrimitive() || String.class.isAssignableFrom(type) || Boolean.class.isAssignableFrom(type) || Character.class.isAssignableFrom(type) || Number.class.isAssignableFrom(type) || java.util.Date.class.isAssignableFrom(type);
    }

    public static boolean isTimeType(Class<?> type) {
        return java.util.Date.class.isAssignableFrom(type);
    }

    public static Boolean isBooleanType(Class<?> type) {
        return Boolean.class.equals(type) || boolean.class.equals(type);
    }

    public static <T> T newInstance(Class<T> clazz, Object... args) {
        try {
            return clazz.getConstructor(getTypes(args)).newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?>[] getTypes(Object... objects) {
        Class<?>[] types = new Class<?>[objects.length];
        for (int i = 0; i < objects.length; i++) {
            types[i] = objects[i].getClass();
        }
        return types;
    }

    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<Field>();
        Collections.addAll(fields, type.getDeclaredFields());
        if (Object.class != type.getSuperclass()) {// 扫描超类的Field
            fields.addAll(getAllFields(type.getSuperclass()));
        }
        return fields;
    }

    public static Field getField(Class<?> type, String field) {
        try {
            return null == field ? null : type.getDeclaredField(field);
        } catch (Exception e) {
            Class<?> superType = type.getSuperclass();
            if (superType.equals(Object.class)) {
                throw new RuntimeException(e + " type=" + type.getName() + ", field=" + field, e);
            } else {
                return getField(superType, field);
            }
        }
    }

    public static Boolean hasField(Class<?> type, String field) {
        try {
            type.getDeclaredField(field);//有此属性
            return true;
        } catch (Exception e) {
            Class<?> superType = type.getSuperclass();
            if (superType.equals(Object.class)) {
                return false;
            } else {
                return hasField(superType, field);
            }
        }
    }

    public static Object getFieldValue(Class<?> type, String fieldName, Object target) {
        try {
            Field field = getField(type, fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Method getMethod(Class<?> type, String methodName, Class<?>... parameterTypes) {
        try {
            return null == methodName ? null : type.getDeclaredMethod(methodName, parameterTypes);
        } catch (Exception e) {
            Class<?> superType = type.getSuperclass();
            if (superType.equals(Object.class)) {
                throw new RuntimeException(e.getMessage() + " type=" + type + ", methodName=" + methodName + ", parameterTypes=" + new NiceToStringList(parameterTypes), e);
            } else {
                return getMethod(superType, methodName, parameterTypes);
            }
        }
    }

    public static Class<?> getActualTypeArgument(Class<?> type, Integer index) {
        Type actualType = getActualTypeArguments(type)[index];
        if (actualType instanceof ParameterizedType) { // Set<String> -> Set.class
            return (Class<?>) ((ParameterizedType) actualType).getRawType();
        } else {
            return (Class<?>) actualType;
        }
    }

    public static Type[] getActualTypeArguments(Class<?> type) {
        Type genericSuperclass = type.getGenericSuperclass();
        if (null == genericSuperclass || !(genericSuperclass instanceof ParameterizedType)) {
            return null;
        } else {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            return parameterizedType.getActualTypeArguments();
        }
    }

    public static Class<?> getFieldType(Class<?> type, String field) {
        int index = field.indexOf('.');
        if (index > 0) {
            String substring = field.substring(0, index);
            String substring2 = field.substring(index + 1);

            return getFieldType(getFieldType(type, substring), substring2);
        } else {
            return getField(type, field).getType();
        }
    }

    public static Boolean hasClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException("className=" + className + ", " + e.getMessage(), e);
        }
    }

    public static Boolean isInterface(String typeName) {
        return classForName(typeName).isInterface();
    }

    /**
     * 返回属性值
     */
    public static Object getFieldValue(Object target, Field field) {
        try {
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回静态属性值
     */
    public static Object getFieldValue(Field field) {
        return getFieldValue(null, field);
    }

    public static String getType(Object obj) {
        if (null == obj) {
            return "obj is null";
        } else {
            return obj.getClass().getName();
        }
    }

    public static Object getFieldValue(Object target, String fieldName) {
        return getFieldValue(target, getField(target.getClass(), fieldName));
    }

    public static Object invoke(Object target, String methodName, Object... args) {
        Method method = getMethod(target.getClass(), methodName, getTypes(args));
        return invoke(method, target, args);
    }

    public static Object invoke(Class<?> type, String methodName, Class<?>[] argTypes, Object target, Object[] args) {
        Method method = getMethod(type, methodName, argTypes);
        return invoke(method, target, args);
    }

    private static Object invoke(Method method, Object target, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Map<String, T> getConsts(Class<?> entityType, Class<T> fieldType) {
        return getConsts(entityType, fieldType, null);
    }

    public static <T> Map<String, T> getConsts(Class<?> entityType, Class<T> fieldType, String startWith) {
        Map<String, T> map = new HashMap<String, T>();
        Field[] fields = entityType.getFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) { // 是常量
                if (StringUtil.isEmpty(startWith) || field.getName().startsWith(startWith)) {
                    map.put(field.getName(), (T) Mirrors.getFieldValue(field));
                }
            }
        }
        return map;
    }

    public static List<Class<?>> getAllInterfaces(Class<?> type) {
        List<Class<?>> interfaces = new ArrayList<Class<?>>();
        interfaces.addAll(Arrays.asList(type.getInterfaces()));
        Class<?> superclass = type.getSuperclass();
        if (!superclass.equals(Object.class)) {
            interfaces.addAll(getAllInterfaces(superclass));
        }
        return interfaces;
    }

    public static void setFieldValue(Object target, String fieldName, Object value) {
        try {
            Field field = getField(target.getClass(), fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean equals(Type[] types1, Type[] types2) {
        if (null != types1 && null != types2 && types1.length == types2.length) {
            for (int i = 0; i < types1.length; i++) {
                if (!types1[i].equals(types2[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static void setFieldValue(Object target, Field field, Object fieldValue) {
        try {
            field.setAccessible(true);
            field.set(target, fieldValue);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}