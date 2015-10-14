package summer.ioc.compiler.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月11日 上午10:30:55)
 * @since Java7
 */
public class JavassistSummerCompilerUtil {
    public static String buildAopTypeInvokeMethodSrc(List<Method> methods) {
        String callDelegateOverrideMethodSrc = "public Object invoke(String methodSignature, Object[] args){";
        for (Method method : methods) {
            callDelegateOverrideMethodSrc += "if(\"" + JavassistSummerCompilerUtil.getMethodSignature(method) + "\".equals(methodSignature)){";
            if ("void".equals(method.getReturnType().getName())) {
                callDelegateOverrideMethodSrc += "super." + method.getName() + "(" + JavassistSummerCompilerUtil.argumentsCasted(method.getParameterTypes()) + ");";
                callDelegateOverrideMethodSrc += "return null;";
            } else {
                callDelegateOverrideMethodSrc += "return super." + method.getName() + "(" + JavassistSummerCompilerUtil.argumentsCasted(method.getParameterTypes()) + ");";
            }
            callDelegateOverrideMethodSrc += "}";
        }
        callDelegateOverrideMethodSrc += "return null;";
        callDelegateOverrideMethodSrc += "}";
        return callDelegateOverrideMethodSrc;
    }

    public static String buildAopTypeCallMethodSrc(List<Method> methods) {
        String callDelegateOverrideMethodSrc = "public Object call(String methodSignature, Object[] args){";
        for (Method method : methods) {
            callDelegateOverrideMethodSrc += "if(\"" + JavassistSummerCompilerUtil.getMethodSignature(method) + "\".equals(methodSignature)){";
            if ("void".equals(method.getReturnType().getName())) {
                callDelegateOverrideMethodSrc += "this." + method.getName() + "(" + JavassistSummerCompilerUtil.argumentsCasted(method.getParameterTypes()) + ");";
                callDelegateOverrideMethodSrc += "return null;";
            } else {
                callDelegateOverrideMethodSrc += "return this." + method.getName() + "(" + JavassistSummerCompilerUtil.argumentsCasted(method.getParameterTypes()) + ");";
            }
            callDelegateOverrideMethodSrc += "}";
        }
        callDelegateOverrideMethodSrc += "return null;";
        callDelegateOverrideMethodSrc += "}";
        return callDelegateOverrideMethodSrc;
    }

    public static String buildOverrideAopMethodSrc(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String returnTypeName = Reflect.typeToJavaCode(method.getReturnType());

        String src = "public " + returnTypeName + " " + method.getName() + "(" + parameters(parameterTypes) + ") {";

        String args;
        if (0 == parameterTypes.length) {
            args = "new Object[0]";
        } else {
            args = "new Object[] { " + arguments(parameterTypes) + " }";
        }

        String methodSignature = getMethodSignature(method);
        if ("void".equals(returnTypeName)) {
            src += "new AopChain(\"" + methodSignature + "\",this, " + args + ", aopTypeMeta).doFilter();";
        } else {
            src += "return (" + method.getReturnType().getName() + ")new AopChain(\"" + methodSignature + "\",this, " + args + ", aopTypeMeta).doFilter().getResult();";
        }
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

    public static String buildCallDelegateOverrideAopMethodSrc(Method method, BeanDefinition beanDefinition, BeanField beanField) {
        Field field = Reflect.getDeclaredField(beanDefinition.getBeanType(), beanField.getName());
        Class<?> fieldType = field.getType();

        Class<?>[] parameterTypes = method.getParameterTypes();
        String src = "public " + Reflect.typeToJavaCode(method.getReturnType()) + " " + method.getName() + "(" + parameters(parameterTypes) + "){";

        if ("void".equals(Reflect.typeToJavaCode(method.getReturnType()))) {
            src += "((" + fieldType.getName() + ")getAopTypeMeta().getReferenceTarget())" + "." + method.getName() + "(" + arguments(parameterTypes) + ");";
        } else {
            src += "return " + "((" + fieldType.getName() + ")getAopTypeMeta().getReferenceTarget())" + "." + method.getName() + "(" + arguments(parameterTypes) + ");";
        }

        src += "}";
        return src;
    }

    private static String argumentsCasted(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }
            src += "(" + Reflect.typeToJavaCode(parameterTypes[i]) + ")args[" + i + "]";
        }
        return src;
    }

    /**
     * 引用实参列表
     */
    private static String arguments(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }
            src += " _param_" + i;
        }
        return src;
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
            src += Reflect.typeToJavaCode(parameterTypes[i]) + " _param_" + i;
        }
        return src;
    }
}