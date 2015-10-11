package summer.ioc.compiler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.ioc.ReferenceType;
import summer.ioc.SummerCompiler;
import summer.util.Javassist;
import summer.util.Reflect;
import summer.util.Stream;

/**
 * @author li
 * @version 1 (2015年10月11日 上午9:49:52)
 * @since Java7
 */
// com.alibaba.dubbo.common.compiler.support.JavassistCompiler
public class JavassistSummerCompiler implements SummerCompiler {
    public Class<?> compileReferenceType(BeanDefinition beanDefinition, BeanField beanField) {
        Field field = Reflect.getField(beanDefinition.getBeanType(), beanField.getName());

        Class<?> fieldType = field.getType();
        String fieldTypeName = fieldType.getName();

        ClassPool classPool = ClassPool.getDefault();

        String subClassName = fieldTypeName + "_JavassistSummerCompiler_Reference";
        CtClass ctClass = classPool.makeClass(subClassName);
        CtClass fieldTypeCtClass = Javassist.getCtClass(classPool, fieldTypeName);
        if (fieldType.isInterface()) {
            ctClass.addInterface(fieldTypeCtClass);
        } else {
            Javassist.setSuperclass(ctClass, fieldTypeCtClass);
        }

        // ReferenceType
        CtClass referenceTypeCtClass = Javassist.getCtClass(classPool, ReferenceType.class.getName());
        ctClass.addInterface(referenceTypeCtClass);

        addIocContextField(ctClass);
        addIocContextFieldSetter(ctClass);

        List<Method> methods = CompilerUtil.getOriginalPublicMethods(fieldType);
        for (Method method : methods) {
            // makeCallDelegateOverrideMethod
            makeCallDelegateOverrideMethod(ctClass, method, beanDefinition, beanField);
        }

        Javassist.ctClassToBytecode(ctClass, Stream.newFileOutputStream("/classes/" + subClassName + ".class"));
        return Javassist.ctClassToClass(ctClass);
    }

    private void addIocContextField(CtClass ctClass) {
        CtField ctField = Javassist.ctFieldWithInitMake("private summer.ioc.IocContext iocContext; ", ctClass);
        Javassist.ctClassAddField(ctClass, ctField);
    }

    private void addIocContextFieldSetter(CtClass ctClass) {
        String iocContextFieldSetterSrc = "public void setIocContext(summer.ioc.IocContext iocContext) { this.iocContext=iocContext; } ";
        CtMethod iocContextFieldSetter = Javassist.ctNewMethodMake(iocContextFieldSetterSrc, ctClass);
        Javassist.ctClassAddMethod(ctClass, iocContextFieldSetter);
    }

    private void makeCallDelegateOverrideMethod(CtClass ctClass, Method method, BeanDefinition beanDefinition, BeanField beanField) {
        String callDelegateOverrideMethodSrc = JavassistSummerCompilerUtil.makeCallDelegateOverrideMethod(method, beanDefinition, beanField);
        CtMethod callDelegateOverrideMethod = Javassist.ctNewMethodMake(callDelegateOverrideMethodSrc, ctClass);
        Javassist.ctClassAddMethod(ctClass, callDelegateOverrideMethod);
    }

    public Class<?> compile(Class<?> originalType) {
        String originalTypeName = originalType.getName();

        ClassPool classPool = ClassPool.getDefault();
        classPool.importPackage("java.lang.reflect.Method");
        classPool.importPackage("summer.aop.AopFilter");
        classPool.importPackage("summer.aop.Invoker");
        classPool.importPackage("summer.aop.AopChain");

        String subClassName = originalTypeName + "_JavassistSummerCompiler_Aop";
        CtClass ctClass = classPool.makeClass(subClassName);
        CtClass superCtClass = Javassist.getCtClass(classPool, originalTypeName);
        Javassist.setSuperclass(ctClass, superCtClass);

        List<Method> methods = CompilerUtil.getOriginalPublicMethods(originalType);
        for (Method method : methods) {
            // make callSuperMethod
            makeCallSuperMethod(ctClass, method);

            // make invoker class
            makeInvokerClass(classPool, subClassName, method);

            // make overrideMethod
            makeOverrideMethod(ctClass, method);
        }

        Javassist.ctClassToBytecode(ctClass, Stream.newFileOutputStream("/classes/" + subClassName + ".class"));
        return Javassist.ctClassToClass(ctClass);
    }

    private void makeOverrideMethod(CtClass ctClass, Method method) {
        String overrideMethodSrc = JavassistSummerCompilerUtil.makeOverrideMethodSrc(method);
        CtMethod overrideSuperMethod = Javassist.ctNewMethodMake(overrideMethodSrc, ctClass);
        Javassist.ctClassAddMethod(ctClass, overrideSuperMethod);
    }

    private void makeInvokerClass(ClassPool classPool, String subClassName, Method method) {
        String methodInvokerTypeName = JavassistSummerCompilerUtil.methodInvokerTypeName(method);
        CtClass invokerCtClass = classPool.makeClass(methodInvokerTypeName);
        CtClass invokerSuperCtClass = Javassist.getCtClass(classPool, "summer.aop.Invoker");
        Javassist.setSuperclass(invokerCtClass, invokerSuperCtClass);

        Class<?>[] parameterTypes = method.getParameterTypes();
        String invokerCtMethodSrc = "public Object invoke() { ";//
        String invokeSuperStatement = "((" + subClassName + ")getTarget()).super_" + method.getName() + "(" + JavassistSummerCompilerUtil.invokerArguments(parameterTypes) + ");";

        if ("void".equals(method.getReturnType().getName())) {
            invokerCtMethodSrc += invokeSuperStatement;
            invokerCtMethodSrc += "return null; ";
        } else {
            invokerCtMethodSrc += " return " + invokeSuperStatement;
        }

        invokerCtMethodSrc += " } ";
        CtMethod invokerCtMethod = Javassist.ctNewMethodMake(invokerCtMethodSrc, invokerCtClass);
        Javassist.ctClassAddMethod(invokerCtClass, invokerCtMethod);

        Javassist.ctClassToBytecode(invokerCtClass, Stream.newFileOutputStream("/classes/" + methodInvokerTypeName + ".class"));
        Javassist.ctClassToClass(invokerCtClass);
    }

    private void makeCallSuperMethod(CtClass ctClass, Method method) {
        String callSuperMethodSrc = JavassistSummerCompilerUtil.makeCallSuperMethodSrc(method);
        CtMethod callSuperMethod = Javassist.ctNewMethodMake(callSuperMethodSrc, ctClass);
        Javassist.ctClassAddMethod(ctClass, callSuperMethod);
    }
}