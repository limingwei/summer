package summer.ioc.compiler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.ioc.MethodPool;
import summer.log.Logger;
import summer.util.Log;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月11日 上午10:30:55)
 * @since Java7
 */
public class JavassistSummerCompilerUtil {
    private static final Logger log = Log.slf4j();

    public static String makeOverrideMethodSrc(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String returnTypeName = method.getReturnType().getName();

        String methodSignature = "public " + returnTypeName + " " + method.getName() + "(" + parameters(parameterTypes) + ")";
        MethodPool.put(method.getDeclaringClass().getName() + " " + methodSignature, method);
        String src = methodSignature + "{";

        if (0 == parameterTypes.length) {
            src += "Object[] args = new Object[0];";
        } else {
            src += "Object[] args = new Object[] { " + arguments(parameterTypes) + " } ;";
        }

        src += "AopFilter[] filters = new AopFilter[]{new summer.aop.LoggerAopFilter()};";
        src += "Invoker invoker = new " + methodInvokerTypeName(method) + "();";
        src += "invoker.setTarget(this);";

        src += "Method method = summer.ioc.MethodPool.get(\"" + method.getDeclaringClass().getName() + " " + methodSignature + "\");"; // 不可为空

        if ("void".equals(returnTypeName)) {
            src += "new AopChain(this, method, args, filters, invoker).doFilter();";
        } else {
            src += "return (" + returnTypeName + ")new AopChain(this, method, args, filters, invoker).doFilter().getResult();";
        }

        src += "}";
        log.info("makeOverrideMethodSrc, src={}", src);
        return src;
    }

    public static String methodInvokerTypeName(Method method) {
        String returnTypeName = method.getReturnType().getName();
        Class<?>[] parameterTypes = method.getParameterTypes();

        String methodInvokerTypeName = method.getDeclaringClass().getName() + ".public_" + returnTypeName.replace('.', '_') + "_" + method.getName();
        for (int i = 0; i < parameterTypes.length; i++) {
            methodInvokerTypeName += "_" + parameterTypes[i].getName().replace('.', '_');
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
        String src = "public " + method.getReturnType().getName() + " super_" + method.getName() + "(" + parameters(parameterTypes) + "){";
        src += "return super." + method.getName() + "(" + arguments(parameterTypes) + ");";
        src += "}";
        return src;
    }

    public static String makeCallDelegateOverrideMethod(Method method, BeanDefinition beanDefinition, BeanField beanField) {
        String getBeanStatement;
        Field field = Reflect.getField(beanDefinition.getBeanType(), beanField.getName());
        Class<?> fieldType = field.getType();
        String beanFieldValue = beanField.getValue();
        if (null == beanFieldValue || beanFieldValue.isEmpty()) {
            getBeanStatement = "((" + fieldType.getName() + ")iocContext.getBean(" + fieldType.getName() + ".class))";
        } else {
            getBeanStatement = "((" + fieldType.getName() + ")iocContext.getBean(" + fieldType.getName() + ".class,\"" + beanFieldValue + "\"))";
        }

        Class<?>[] parameterTypes = method.getParameterTypes();
        String src = "public " + method.getReturnType().getName() + " " + method.getName() + "(" + parameters(parameterTypes) + "){";

        if ("void".equals(method.getReturnType().getName())) {
            src += getBeanStatement + "." + method.getName() + "(" + arguments(parameterTypes) + ");";
        } else {
            src += "return " + getBeanStatement + "." + method.getName() + "(" + arguments(parameterTypes) + ");";
        }

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
            src += " _param_" + i;
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
            src += parameterTypes[i].getName() + " _param_" + i;
        }
        return src;
    }
}