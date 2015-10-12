package summer.ioc.compiler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import summer.aop.Aop;
import summer.aop.AopInvoker;
import summer.aop.Transaction;
import summer.aop.TransactionAopFilter;
import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.ioc.MethodPool;
import summer.log.Logger;
import summer.mvc.ParameterAdapter;
import summer.mvc.ViewProcessor;
import summer.mvc.annotation.At;
import summer.mvc.aop.ViewProcessorAopFilter;
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
        String returnTypeName = Reflect.typeToJavaCode(method.getReturnType());

        String methodSignature = "public " + returnTypeName + " " + method.getName() + "(" + parameters(parameterTypes) + ")";
        MethodPool.putMethod(method.getDeclaringClass().getName() + " " + methodSignature, method);
        String src = methodSignature + "{";

        if (0 == parameterTypes.length) {
            src += "Object[] args = new Object[0];";
        } else {
            src += "Object[] args = new Object[] { " + arguments(parameterTypes) + " } ;";
        }

        src += "AopFilter[] filters = " + aopFiltersSrc(method) + ";";
        src += AopInvoker.class.getName() + " invoker = new " + methodInvokerTypeName(method) + "();";

        src += "Method method = summer.ioc.MethodPool.getMethod(\"" + method.getDeclaringClass().getName() + " " + methodSignature + "\");"; // 不可为空

        if ("void".equals(returnTypeName)) {
            src += "new AopChain(this, method, args, filters, invoker).doFilter();";
        } else {
            src += "return (" + returnTypeName + ")new AopChain(this, method, args, filters, invoker).doFilter().getResult();";
        }

        src += "}";
        return src;
    }

    public static String aopFiltersSrc(Method method) {
        String aopFiltersSrc = "";
        if (null != method.getAnnotation(At.class)) {
            String parameterAdapterClassName = ParameterAdapter.class.getName();
            String parameterAdapterAopFilterClassName = summer.mvc.aop.ParameterAdapterAopFilter.class.getName();
            aopFiltersSrc += "new " + parameterAdapterAopFilterClassName + "((" + parameterAdapterClassName + ")iocContext.getBean(" + parameterAdapterClassName + ".class))";
        }
        Aop aop = method.getAnnotation(Aop.class);
        if (null != aop) {
            for (String aopBeanName : aop.value()) {
                aopFiltersSrc += " , iocContext.getBean(\"" + aopBeanName + "\")";
            }
        }
        Transaction transaction = method.getAnnotation(Transaction.class);
        if (null != transaction) {
            aopFiltersSrc += " , iocContext.getBean(" + TransactionAopFilter.class.getName() + ".class)";
        }
        if (null != method.getAnnotation(At.class)) {
            String viewProcessorClassName = ViewProcessor.class.getName();
            String viewProcessorAopFilterClassName = ViewProcessorAopFilter.class.getName();
            aopFiltersSrc += ", new " + viewProcessorAopFilterClassName + "((" + viewProcessorClassName + ")iocContext.getBean(" + viewProcessorClassName + ".class))";
        }

        if (aopFiltersSrc.isEmpty()) {
            return " new AopFilter[0]";
        } else {
            return "new AopFilter[]{" + aopFiltersSrc + "}";
        }
    }

    public static String methodInvokerTypeName(Method method) {
        String returnTypeName = Reflect.typeToJavaCode(method.getReturnType()).replace("[]", "_Array");
        Class<?>[] parameterTypes = method.getParameterTypes();

        String methodInvokerTypeName = method.getDeclaringClass().getName() + ".public_" + returnTypeName.replace('.', '_') + "_" + method.getName();
        for (int i = 0; i < parameterTypes.length; i++) {
            methodInvokerTypeName += "_" + Reflect.typeToJavaCode(parameterTypes[i]).replace("[]", "_Array").replace('.', '_');
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
            src += "(" + Reflect.typeToJavaCode(parameterTypes[i]) + ")args[" + i + "]";
        }
        return src;
    }

    public static String makeCallSuperMethodSrc(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String src = "public " + Reflect.typeToJavaCode(method.getReturnType()) + " super_" + method.getName() + "(" + parameters(parameterTypes) + "){";
        src += "return super." + method.getName() + "(" + arguments(parameterTypes) + ");";
        src += "}";
        return src;
    }

    public static String makeCallDelegateOverrideMethod(Method method, BeanDefinition beanDefinition, BeanField beanField) {
        Field field = Reflect.getField(beanDefinition.getBeanType(), beanField.getName());
        Class<?> fieldType = field.getType();

        Class<?>[] parameterTypes = method.getParameterTypes();
        String src = "public " + Reflect.typeToJavaCode(method.getReturnType()) + " " + method.getName() + "(" + parameters(parameterTypes) + "){";

        if ("void".equals(Reflect.typeToJavaCode(method.getReturnType()))) {
            src += "((" + fieldType.getName() + ")getReferenceTarget())" + "." + method.getName() + "(" + arguments(parameterTypes) + ");";
        } else {
            src += "return " + "((" + fieldType.getName() + ")getReferenceTarget())" + "." + method.getName() + "(" + arguments(parameterTypes) + ");";
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
            src += Reflect.typeToJavaCode(parameterTypes[i]) + " _param_" + i;
        }
        return src;
    }
}