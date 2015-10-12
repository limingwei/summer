package summer.ioc.compiler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import summer.aop.AopChain;
import summer.aop.AopFilter;
import summer.aop.AopInvoker;
import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.ioc.IocContextAware;
import summer.ioc.SummerCompiler;
import summer.util.JavassistUtil;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月11日 上午9:49:52)
 * @since Java7
 */
// com.alibaba.dubbo.common.compiler.support.JavassistCompiler
public class JavassistSummerCompiler implements SummerCompiler {
    public Class<?> compileClass(Class<?> originalType) {
        String originalTypeName = originalType.getName();

        ClassPool classPool = new ClassPool(true);
        classPool.appendClassPath(new LoaderClassPath(getClass().getClassLoader()));

        classPool.importPackage(Method.class.getName());
        classPool.importPackage(AopFilter.class.getName());
        classPool.importPackage(AopInvoker.class.getName());
        classPool.importPackage(AopChain.class.getName());

        String subClassName = originalTypeName + "_JavassistSummerCompiler_Aop";
        CtClass ctClass = classPool.makeClass(subClassName);
        CtClass superCtClass = JavassistUtil.getCtClass(classPool, originalTypeName);
        JavassistUtil.setSuperclass(ctClass, superCtClass);

        // IocContextAware
        CtClass iocContextAwareCtClass = JavassistUtil.getCtClass(classPool, IocContextAware.class.getName());
        ctClass.addInterface(iocContextAwareCtClass);

        addIocContextField(ctClass);
        addIocContextFieldSetter(ctClass);

        List<Method> methods = Reflect.getPublicMethods(originalType);
        for (Method method : methods) {
            // make callSuperMethod
            makeCallSuperMethod(ctClass, method);

            // make invoker class
            makeInvokerClass(classPool, subClassName, method);

            // make overrideMethod
            makeOverrideMethod(ctClass, method);
        }

        return JavassistUtil.ctClassToClass(ctClass);
    }

    public Class<?> compileReference(BeanDefinition beanDefinition, BeanField beanField) {
        Field field = Reflect.getField(beanDefinition.getBeanType(), beanField.getName());

        Class<?> fieldType = field.getType();
        String fieldTypeName = fieldType.getName();

        ClassPool classPool = new ClassPool(true);
        classPool.appendClassPath(new LoaderClassPath(getClass().getClassLoader()));

        String subClassName = fieldTypeName + "_JavassistSummerCompiler_Reference";
        CtClass ctClass = classPool.makeClass(subClassName);
        CtClass fieldTypeCtClass = JavassistUtil.getCtClass(classPool, fieldTypeName);
        if (fieldType.isInterface()) {
            ctClass.addInterface(fieldTypeCtClass);
        } else {
            JavassistUtil.setSuperclass(ctClass, fieldTypeCtClass);
        }

        // IocContextAware
        CtClass iocContextAwareCtClass = JavassistUtil.getCtClass(classPool, IocContextAware.class.getName());
        ctClass.addInterface(iocContextAwareCtClass);

        addIocContextField(ctClass);
        addIocContextFieldSetter(ctClass);

        addReferenceTargetField(ctClass);
        addReferenceTargetFieldGetter(ctClass, beanDefinition, beanField);

        List<Method> methods = Reflect.getPublicMethods(fieldType);
        for (Method method : methods) {
            // makeCallDelegateOverrideMethod
            makeCallDelegateOverrideMethod(ctClass, method, beanDefinition, beanField);
        }

        return JavassistUtil.ctClassToClass(ctClass);
    }

    private void addReferenceTargetField(CtClass ctClass) {
        CtField ctField = JavassistUtil.ctFieldWithInitMake("private java.lang.Object referenceTarget; ", ctClass);
        JavassistUtil.ctClassAddField(ctClass, ctField);
    }

    private void addReferenceTargetFieldGetter(CtClass ctClass, BeanDefinition beanDefinition, BeanField beanField) {
        String referenceTargetFieldGetterSrc = "public java.lang.Object getReferenceTarget() { ";
        referenceTargetFieldGetterSrc += " if(null==this.referenceTarget) { ";

        Field field = Reflect.getField(beanDefinition.getBeanType(), beanField.getName());
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

        CtMethod iocContextFieldSetter = JavassistUtil.ctNewMethodMake(referenceTargetFieldGetterSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, iocContextFieldSetter);
    }

    private void addIocContextField(CtClass ctClass) {
        CtField ctField = JavassistUtil.ctFieldWithInitMake("private summer.ioc.IocContext iocContext; ", ctClass);
        JavassistUtil.ctClassAddField(ctClass, ctField);
    }

    private void addIocContextFieldSetter(CtClass ctClass) {
        String iocContextFieldSetterSrc = "public void setIocContext(summer.ioc.IocContext iocContext) { this.iocContext=iocContext; } ";
        CtMethod iocContextFieldSetter = JavassistUtil.ctNewMethodMake(iocContextFieldSetterSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, iocContextFieldSetter);
    }

    private void makeCallDelegateOverrideMethod(CtClass ctClass, Method method, BeanDefinition beanDefinition, BeanField beanField) {
        String callDelegateOverrideMethodSrc = JavassistSummerCompilerUtil.makeCallDelegateOverrideMethod(method, beanDefinition, beanField);
        CtMethod callDelegateOverrideMethod = JavassistUtil.ctNewMethodMake(callDelegateOverrideMethodSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, callDelegateOverrideMethod);
    }

    private void makeOverrideMethod(CtClass ctClass, Method method) {
        String overrideMethodSrc = JavassistSummerCompilerUtil.makeOverrideMethodSrc(method);
        CtMethod overrideSuperMethod = JavassistUtil.ctNewMethodMake(overrideMethodSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, overrideSuperMethod);
    }

    private void makeInvokerClass(ClassPool classPool, String subClassName, Method method) {
        String methodInvokerTypeName = JavassistSummerCompilerUtil.methodInvokerTypeName(method);
        CtClass invokerCtClass = classPool.makeClass(methodInvokerTypeName);
        CtClass invokerSuperCtClass = JavassistUtil.getCtClass(classPool, AopInvoker.class.getName());
        JavassistUtil.setSuperclass(invokerCtClass, invokerSuperCtClass);

        Class<?>[] parameterTypes = method.getParameterTypes();
        String invokerCtMethodSrc = "public Object invoke() { ";//
        String invokeSuperStatement = "((" + subClassName + ")getTarget()).super_" + method.getName() + "(" + JavassistSummerCompilerUtil.invokerArguments(parameterTypes) + ");";

        if ("void".equals(Reflect.typeToJavaCode(method.getReturnType()))) {
            invokerCtMethodSrc += invokeSuperStatement;
            invokerCtMethodSrc += "return null;";
        } else {
            invokerCtMethodSrc += " return " + invokeSuperStatement;
        }

        invokerCtMethodSrc += " } ";
        CtMethod invokerCtMethod = JavassistUtil.ctNewMethodMake(invokerCtMethodSrc, invokerCtClass);
        JavassistUtil.ctClassAddMethod(invokerCtClass, invokerCtMethod);

        JavassistUtil.ctClassToClass(invokerCtClass);
    }

    private void makeCallSuperMethod(CtClass ctClass, Method method) {
        String callSuperMethodSrc = JavassistSummerCompilerUtil.makeCallSuperMethodSrc(method);
        CtMethod callSuperMethod = JavassistUtil.ctNewMethodMake(callSuperMethodSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, callSuperMethod);
    }
}