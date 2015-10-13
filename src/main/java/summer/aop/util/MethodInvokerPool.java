package summer.aop.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import summer.ioc.compiler.JavassistSummerCompilerUtil;
import summer.util.JavassistUtil;
import summer.util.Reflect;

public class MethodInvokerPool {
    private static final Map<Method, MethodInvoker> CACHE_MAP = new HashMap<Method, MethodInvoker>();

    public static MethodInvoker getMethodInvoker(Method method) {
        MethodInvoker invoker = CACHE_MAP.get(method);
        if (null == invoker) {
            synchronized (CACHE_MAP) {
                invoker = CACHE_MAP.get(method);
                if (null == invoker) {
                    Class<?> buildMethodInvoker = buildMethodInvoker(method);
                    MethodInvoker newInstance = (MethodInvoker) Reflect.newInstance(buildMethodInvoker);
                    CACHE_MAP.put(method, newInstance);
                    invoker = newInstance;
                }
            }
        }
        return invoker;
    }

    private static Class<?> buildMethodInvoker(Method method) {
        ClassPool classPool = new ClassPool(true);
        classPool.appendClassPath(new LoaderClassPath(MethodInvoker.class.getClassLoader()));

        String subClassName = method.toString().replace('(', '1').replace(')', '2').replace(' ', '3').replace('.', '4').replace(',', '5');
        CtClass invokerCtClass = classPool.makeClass(subClassName);
        CtClass aopInvokerCtClass = JavassistUtil.getCtClass(classPool, MethodInvoker.class.getName());
        invokerCtClass.addInterface(aopInvokerCtClass);

        String invokerCtMethodSrc = buildInvokerClassSrc(subClassName, method);

        CtMethod invokerCtMethod = JavassistUtil.ctNewMethodMake(invokerCtMethodSrc, invokerCtClass);
        JavassistUtil.ctClassAddMethod(invokerCtClass, invokerCtMethod);

        return JavassistUtil.ctClassToClass(invokerCtClass);
    }

    private static String buildInvokerClassSrc(String subClassName, Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        String invokerCtMethodSrc = "public Object invoke(Object target, Object[] args) { ";//
        String invokeSuperStatement = "((" + method.getDeclaringClass().getName() + ")target)." + method.getName() + "(" + JavassistSummerCompilerUtil._invoker_arguments(parameterTypes) + ");";

        if ("void".equals(Reflect.typeToJavaCode(method.getReturnType()))) {
            invokerCtMethodSrc += invokeSuperStatement;
            invokerCtMethodSrc += "return null;";
        } else {
            invokerCtMethodSrc += " return " + invokeSuperStatement;
        }

        invokerCtMethodSrc += " } ";
        return invokerCtMethodSrc;
    }
}