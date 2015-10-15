package summer.ioc.compiler.util;

import java.lang.reflect.Method;
import java.util.List;

import summer.aop.AopChain;

/**
 * @author li
 * @version 1 (2015年10月11日 上午10:30:55)
 * @since Java7
 */
public class JavassistSummerCompilerUtil {
    public static String buildAopTypeInvokeMethodSrc(List<Method> methods) {
        String src = "public Object invoke(String methodSignature, Object[] args){";

        for (Method method : methods) {
            String methodSignature = JavassistSummerCompilerUtil.getMethodSignature(method);
            String argumentsCasted = JavassistSummerCompilerUtil.argumentsCasted(method.getParameterTypes());
            String methodName = method.getName();
            Class<?> returnType = method.getReturnType();

            src += "if(\"" + methodSignature + "\".equals(methodSignature)){";
            String invokeSuper = "super." + methodName + "(" + argumentsCasted + ")";
            if ("void".equals(returnType.getName())) {
                src += invokeSuper + "; return null;";
            } else {
                src += "return " + primitiveToObject(returnType, invokeSuper) + ";";
            }
            src += "}";
        }
        src += " return null; ";
        src += "}";

        return src;
    }

    public static String buildOverrideAopMethodSrc(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        Class<?> returnType = method.getReturnType();
        String methodSignature = "\"" + getMethodSignature(method) + "\"";

        String src = "public " + typeToJavaCode(returnType) + " " + method.getName() + "(" + parameters(parameterTypes) + ") {";

        String args = (0 == parameterTypes.length) ? "new Object[0]" : "new Object[] { " + argumentsPrimitived(parameterTypes) + " }";

        String aopChainDoFilter = "new " + AopChain.class.getName() + "(" + methodSignature + ", " + args + ", getAopTypeMeta()).doFilter()";
        if ("void".equals(returnType.getName())) {
            src += aopChainDoFilter + ";";
        } else {
            src += "return " + objectToPrimitive(returnType, aopChainDoFilter + ".getResult()") + ";";
        }
        src += "}";
        return src;
    }

    public static String buildAopTypeCallMethodSrc(List<Method> methods) {
        String src = "public Object call(String methodSignature, Object[] args){";
        for (Method method : methods) {
            String methodSignature = JavassistSummerCompilerUtil.getMethodSignature(method);
            String argumentsCasted = JavassistSummerCompilerUtil.argumentsCasted(method.getParameterTypes());
            Class<?> returnType = method.getReturnType();
            String methodName = method.getName();

            src += "if(\"" + methodSignature + "\".equals(methodSignature)){";
            String returnValue = "this." + methodName + "(" + argumentsCasted + ")";
            if ("void".equals(returnType.getName())) {
                src += returnValue + "; return null;";
            } else {
                String primitiveTypeToObject = primitiveToObject(returnType, returnValue);
                src += "return " + primitiveTypeToObject + ";";
            }
            src += "}";
        }
        src += " return null; ";
        src += "}";

        return src;
    }

    /**
     * 获得方法签名
     */
    public static String getMethodSignature(Method method) {
        String genericString = method.toGenericString();
        String declaringClassName = method.getDeclaringClass().getName();
        int a = genericString.indexOf(" " + declaringClassName + ".");
        int b = genericString.lastIndexOf(')');
        return genericString.substring(a + declaringClassName.length() + 2, b + 1);
    }

    /**
     * 申明形参列表
     */
    private static String parameters(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }
            src += typeToJavaCode(parameterTypes[i]) + " _param_" + i;
        }
        return src;
    }

    /**
     * 引用实参列表 参数名为 _param_1
     */
    private static String argumentsPrimitived(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }

            Class<?> type = parameterTypes[i];
            String value = "_param_" + i;
            src += primitiveToObject(type, value);
        }

        return src;
    }

    /**
     * 引用实参列表 参数为(String)args[]
     */
    private static String argumentsCasted(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }

            Class<?> type = parameterTypes[i];
            String value = "args[" + i + "]";
            src += objectToPrimitive(type, value);
        }
        return src;
    }

    private static String primitiveToObject(Class<?> type, String value) {
        String methodReturnTypeName = type.getName();
        if ("byte".equals(methodReturnTypeName)) {
            return "java.lang.Byte.valueOf(" + value + ")";
        } else if ("short".equals(methodReturnTypeName)) {
            return "java.lang.Short.valueOf(" + value + ")";
        } else if ("int".equals(methodReturnTypeName)) {
            return "java.lang.Integer.valueOf(" + value + ")";
        } else if ("long".equals(methodReturnTypeName)) {
            return "java.lang.Long.valueOf(" + value + ")";
        } else if ("double".equals(methodReturnTypeName)) {
            return "java.lang.Double.valueOf(" + value + ")";
        } else if ("float".equals(methodReturnTypeName)) {
            return "java.lang.Float.valueOf(" + value + ")";
        } else if ("boolean".equals(methodReturnTypeName)) {
            return "java.lang.Boolean.valueOf(" + value + ")";
        } else if ("char".equals(methodReturnTypeName)) {
            return "java.lang.Character.valueOf(" + value + ")";
        } else {
            return value;
        }
    }

    private static String objectToPrimitive(Class<?> type, String value) {
        String methodReturnTypeName = type.getName();
        if ("byte".equals(methodReturnTypeName)) {
            return "summer.util.PrimitiveTypeUtil.toPrimitive((java.lang.Byte)" + value + ")";
        } else if ("short".equals(methodReturnTypeName)) {
            return "summer.util.PrimitiveTypeUtil.toPrimitive((java.lang.Short)" + value + ")";
        } else if ("int".equals(methodReturnTypeName)) {
            return "summer.util.PrimitiveTypeUtil.toPrimitive((java.lang.Integer)" + value + ")";
        } else if ("long".equals(methodReturnTypeName)) {
            return "summer.util.PrimitiveTypeUtil.toPrimitive((java.lang.Long)" + value + ")";
        } else if ("double".equals(methodReturnTypeName)) {
            return "summer.util.PrimitiveTypeUtil.toPrimitive((java.lang.Double)" + value + ")";
        } else if ("float".equals(methodReturnTypeName)) {
            return "summer.util.PrimitiveTypeUtil.toPrimitive((java.lang.Float)" + value + ")";
        } else if ("boolean".equals(methodReturnTypeName)) {
            return "summer.util.PrimitiveTypeUtil.toPrimitive((java.lang.Boolean)" + value + ")";
        } else if ("char".equals(methodReturnTypeName)) {
            return "summer.util.PrimitiveTypeUtil.toPrimitive((java.lang.Character)" + value + ")";
        } else {
            return "(" + typeToJavaCode(type) + ")" + value;
        }
    }

    public static String typeToJavaCode(Class<?> type) {
        if (type.isArray()) {
            return type.getComponentType().getName() + "[]";
        } else {
            return type.getName();
        }
    }
}