package summer.ioc.compiler;

import java.lang.reflect.Method;

/**
 * @author li
 * @version 1 (2015年10月11日 上午10:30:55)
 * @since Java7
 */
public class JavassistSummerCompilerUtil {
    public static String makeOverrideMethodSrc(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String returnTypeName = method.getReturnType().getName();

        String src = "public " + returnTypeName + " " + method.getName() + "(" + parameters(parameterTypes) + ")" + "{";
        src += method.getDeclaringClass().getName() + " target = this;";
        src += "Method method = null;"; // 不可为空
        if (0 == parameterTypes.length) {
            src += "Object[] args = new Object[0];";
        }else {
            src += "Object[] args = new Object[] { " + arguments(parameterTypes) + " } ;";
        }
        src += "AopFilter[] filters = new AopFilter[]{new summer.aop.AopFilter111()};";
        src += "Invoker invoker = new " + methodInvokerTypeName(method) + "();";
        src += "invoker.setTarget(target);";
        if ("void".equals(returnTypeName)) {
            src += "new AopChain(target, method, args, filters, invoker).doFilter().getResult();";
        } else {
            src += "return (" + returnTypeName + ")new AopChain(target, method, args, filters, invoker).doFilter().getResult();";
        }
        src += "}";
        return src;
    }

    public static String methodInvokerTypeName(Method method) {
        String returnTypeName = method.getReturnType().getName();
        Class<?>[] parameterTypes = method.getParameterTypes();

        String methodInvokerTypeName = method.getDeclaringClass().getName() + ".public_" + returnTypeName.replace('.', '$') + "_" + method.getName();
        for (int i = 0; i < parameterTypes.length; i++) {
            methodInvokerTypeName += "_" + parameterTypes[i].getName().replace('.', '$');
        }
        methodInvokerTypeName += "_Invoker";
        return methodInvokerTypeName;
    }

    public static String invokerArguments(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }
            src += "(" + parameterTypes[i].getName() + ")getArgs()[" + i + "]";
        }
        return src;
    }

    public static String makeCallSuperMethodSrc(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String src = "public " + method.getReturnType().getName() + " super$" + method.getName() + "(" + parameters(parameterTypes) + "){";
        src += "return super." + method.getName() + "(" + arguments(parameterTypes) + ");";
        src += "}";
        return src;
    }

    /**
     * 引用实参列表
     */
    public static String arguments(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }
            src += " $param_" + i;
        }
        return src;
    }

    /**
     * 申明形参列表
     */
    public static String parameters(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }
            src += parameterTypes[i].getName() + " $param_" + i;
        }
        return src;
    }
}