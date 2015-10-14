package summer.ioc.compiler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月11日 上午10:30:55)
 * @since Java7
 */
public class JavassistSummerCompilerUtil {
    public static String buildOverrideMethodSrc(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String returnTypeName = Reflect.typeToJavaCode(method.getReturnType());

        String methodSignature = "public " + returnTypeName + " " + method.getName() + "(" + _parameters(parameterTypes) + ")";
        String src = methodSignature + "{";

        String args;
        if (0 == parameterTypes.length) {
            args = "new Object[0]";
        } else {
            args = "new Object[] { " + _arguments(parameterTypes) + " }";
        }

        String methodSign = getMethodSignature(method);
        if ("void".equals(returnTypeName)) {
            src += "new AopChain(\"" + methodSign + "\",this, " + args + ", aopTypeMeta).doFilter();";
        } else {
            src += "return (" + method.getReturnType().getName() + ")new AopChain(\"" + methodSign + "\",this, " + args + ", aopTypeMeta).doFilter().getResult();";
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

    public static String _invoker_arguments(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }
            src += "(" + Reflect.typeToJavaCode(parameterTypes[i]) + ")args[" + i + "]";
        }
        return src;
    }

    public static String buildCallDelegateOverrideMethodSrc(Method method, BeanDefinition beanDefinition, BeanField beanField) {
        Field field = Reflect.getDeclaredField(beanDefinition.getBeanType(), beanField.getName());
        Class<?> fieldType = field.getType();

        Class<?>[] parameterTypes = method.getParameterTypes();
        String src = "public " + Reflect.typeToJavaCode(method.getReturnType()) + " " + method.getName() + "(" + _parameters(parameterTypes) + "){";

        if ("void".equals(Reflect.typeToJavaCode(method.getReturnType()))) {
            src += "((" + fieldType.getName() + ")getAopTypeMeta().getReferenceTarget())" + "." + method.getName() + "(" + _arguments(parameterTypes) + ");";
        } else {
            src += "return " + "((" + fieldType.getName() + ")getAopTypeMeta().getReferenceTarget())" + "." + method.getName() + "(" + _arguments(parameterTypes) + ");";
        }

        src += "}";
        return src;
    }

    /**
     * 引用实参列表
     */
    private static String _arguments(Class<?>[] parameterTypes) {
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
    private static String _parameters(Class<?>[] parameterTypes) {
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