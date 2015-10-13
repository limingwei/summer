package summer.ioc.compiler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import summer.aop.Aop;
import summer.aop.AopInvoker;
import summer.aop.Transaction;
import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.ioc.MethodPool;
import summer.mvc.annotation.At;
import summer.util.Reflect;
import summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年10月11日 上午10:30:55)
 * @since Java7
 */
public class JavassistSummerCompilerUtil {
    public static String buildInvokerClassSrc(String subClassName, Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        String invokerCtMethodSrc = "public Object invoke(Object target, Object[] args) { ";//
        String invokeSuperStatement = "((" + subClassName + ")target).super_" + method.getName() + "(" + JavassistSummerCompilerUtil._invoker_arguments(parameterTypes) + ");";

        if ("void".equals(Reflect.typeToJavaCode(method.getReturnType()))) {
            invokerCtMethodSrc += invokeSuperStatement;
            invokerCtMethodSrc += "return null;";
        } else {
            invokerCtMethodSrc += " return " + invokeSuperStatement;
        }

        invokerCtMethodSrc += " } ";
        return invokerCtMethodSrc;
    }

    public static String buildReferenceTargetFieldGetterSrc(BeanDefinition beanDefinition, BeanField beanField) {
        String referenceTargetFieldGetterSrc = "public java.lang.Object getReferenceTarget() { ";
        referenceTargetFieldGetterSrc += " if(null==this.referenceTarget) { ";

        Field field = Reflect.getDeclaredField(beanDefinition.getBeanType(), beanField.getName());
        Class<?> fieldType = field.getType();
        String beanFieldValue = beanField.getValue();
        if (null == beanFieldValue || beanFieldValue.isEmpty()) {
            referenceTargetFieldGetterSrc += "this.referenceTarget = iocContext.getBean(" + fieldType.getName() + ".class);";
        } else {
            referenceTargetFieldGetterSrc += "this.referenceTarget = iocContext.getBean(" + fieldType.getName() + ".class,\"" + beanFieldValue + "\");";
        }

        referenceTargetFieldGetterSrc += " } ";
        referenceTargetFieldGetterSrc += " return this.referenceTarget; ";
        referenceTargetFieldGetterSrc += " } ";
        return referenceTargetFieldGetterSrc;
    }

    public static String buildOverrideMethodSrc(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String returnTypeName = Reflect.typeToJavaCode(method.getReturnType());

        String methodSignature = "public " + returnTypeName + " " + method.getName() + "(" + _parameters(parameterTypes) + ")";
        MethodPool.putMethod(method.getDeclaringClass().getName() + " " + methodSignature, method);
        String src = methodSignature + "{";

        if (0 == parameterTypes.length) {
            src += "Object[] args = new Object[0];";
        } else {
            src += "Object[] args = new Object[] { " + _arguments(parameterTypes) + " } ;";
        }

        src += "AopFilter[] filters = " + _aop_filters_src(method) + ";";
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

    private static String _aop_filters_src(Method method) {
        List<String> aopFilters = new ArrayList<String>();
        if (null != method.getAnnotation(At.class)) { // 类型
            aopFilters.add("summer.aop.util.AopUtil.getParameterAdapterAopFilter(iocContext)");
        }

        Aop aop = method.getAnnotation(Aop.class);
        if (null != aop) {
            for (String aopBeanName : aop.value()) { // 名称
                aopFilters.add("summer.aop.util.AopUtil.getAopFilter(iocContext,\"" + aopBeanName + "\")");
            }
        }

        Transaction transaction = method.getAnnotation(Transaction.class);
        if (null != transaction) { // 名称
            aopFilters.add("summer.aop.util.AopUtil.getTransactionAopFilter(iocContext)");
        }

        if (null != method.getAnnotation(At.class)) { // 类型
            aopFilters.add("summer.aop.util.AopUtil.getViewProcessorAopFilter(iocContext)");
        }

        if (aopFilters.isEmpty()) {
            return " new AopFilter[0]";
        } else {
            return "new AopFilter[]{" + StringUtil.join(aopFilters, ",") + "}";
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

    private static String _invoker_arguments(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }
            src += "(" + Reflect.typeToJavaCode(parameterTypes[i]) + ")args[" + i + "]";
        }
        return src;
    }

    public static String buildCallSuperMethodSrc(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String src = "public " + Reflect.typeToJavaCode(method.getReturnType()) + " super_" + method.getName() + "(" + _parameters(parameterTypes) + "){";
        src += "return super." + method.getName() + "(" + _arguments(parameterTypes) + ");";
        src += "}";
        return src;
    }

    public static String buildCallDelegateOverrideMethodSrc(Method method, BeanDefinition beanDefinition, BeanField beanField) {
        Field field = Reflect.getDeclaredField(beanDefinition.getBeanType(), beanField.getName());
        Class<?> fieldType = field.getType();

        Class<?>[] parameterTypes = method.getParameterTypes();
        String src = "public " + Reflect.typeToJavaCode(method.getReturnType()) + " " + method.getName() + "(" + _parameters(parameterTypes) + "){";

        if ("void".equals(Reflect.typeToJavaCode(method.getReturnType()))) {
            src += "((" + fieldType.getName() + ")getReferenceTarget())" + "." + method.getName() + "(" + _arguments(parameterTypes) + ");";
        } else {
            src += "return " + "((" + fieldType.getName() + ")getReferenceTarget())" + "." + method.getName() + "(" + _arguments(parameterTypes) + ");";
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