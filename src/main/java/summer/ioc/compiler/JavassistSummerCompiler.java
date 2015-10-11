package summer.ioc.compiler;

import java.lang.reflect.Method;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import summer.ioc.SummerCompiler;
import summer.util.Javassist;
import summer.util.Stream;

/**
 * @author li
 * @version 1 (2015年10月11日 上午9:49:52)
 * @since Java7
 */
// com.alibaba.dubbo.common.compiler.support.JavassistCompiler
public class JavassistSummerCompiler implements SummerCompiler {
    public Class<?> compile(Class<?> originalType) {
        String originalTypeName = originalType.getName();

        ClassPool classPool = ClassPool.getDefault();
        classPool.importPackage("java.lang.reflect.Method");
        classPool.importPackage("summer.aop.AopFilter");
        classPool.importPackage("summer.aop.Invoker");
        classPool.importPackage("summer.aop.AopChain");
        classPool.importPackage("java.util.Map");
        classPool.importPackage("java.util.HashMap");

        String subClassName = originalTypeName + "$JavassistSummerCompiler";
        CtClass ctClass = classPool.makeClass(subClassName);
        CtClass superCtClass = Javassist.getCtClass(classPool, originalTypeName);
        Javassist.setSuperclass(ctClass, superCtClass);

        List<Method> methods = CompilerUtil.getOriginalPublicMethods(originalType);
        for (Method method : methods) {
            // make callSuperMethod
            String callSuperMethodSrc = JavassistSummerCompilerUtil.makeCallSuperMethodSrc(method);
            CtMethod callSuperMethod = Javassist.ctNewMethodMake(callSuperMethodSrc, ctClass);
            Javassist.ctClassAddMethod(ctClass, callSuperMethod);

            // make invoker class
            String methodInvokerTypeName = JavassistSummerCompilerUtil.methodInvokerTypeName(method);
            CtClass invokerCtClass = classPool.makeClass(methodInvokerTypeName);
            CtClass invokerSuperCtClass = Javassist.getCtClass(classPool, "summer.aop.Invoker");
            Javassist.setSuperclass(invokerCtClass, invokerSuperCtClass);

            Class<?>[] parameterTypes = method.getParameterTypes();
            String invokerCtMethodSrc = "public Object invoke() { return ((" + subClassName + ")getTarget()).super$" + method.getName() + "(" + JavassistSummerCompilerUtil.invokerArguments(parameterTypes) + ");" + " } ";
            CtMethod invokerCtMethod = Javassist.ctNewMethodMake(invokerCtMethodSrc, invokerCtClass);
            Javassist.ctClassAddMethod(invokerCtClass, invokerCtMethod);
            Javassist.ctClassToBytecode(invokerCtClass, Stream.newFileOutputStream("/classes/" + methodInvokerTypeName + ".class"));
            Javassist.ctClassToClass(invokerCtClass);

            // make overrideMethod
            String overrideMethodSrc = JavassistSummerCompilerUtil.makeOverrideMethodSrc(method);
            CtMethod overrideSuperMethod = Javassist.ctNewMethodMake(overrideMethodSrc, ctClass);
            Javassist.ctClassAddMethod(ctClass, overrideSuperMethod);
        }

        Javassist.ctClassToBytecode(ctClass, Stream.newFileOutputStream("/classes/" + subClassName + ".class"));
        return Javassist.ctClassToClass(ctClass);
    }
}