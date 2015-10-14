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
import summer.aop.AopType;
import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.ioc.SummerCompiler;
import summer.ioc.compiler.util.JavassistSummerCompilerUtil;
import summer.log.Logger;
import summer.util.JavassistUtil;
import summer.util.Log;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月11日 上午9:49:52)
 * @since Java7
 */
// com.alibaba.dubbo.common.compiler.support.JavassistCompiler
public class JavassistSummerCompiler implements SummerCompiler {
    private static final Logger log = Log.slf4j();

    public Class<?> compileClass(Class<?> originalType) {
        String originalTypeName = originalType.getName();

        ClassPool classPool = new ClassPool(true);
        classPool.appendClassPath(new LoaderClassPath(getClass().getClassLoader()));

        classPool.importPackage(AopChain.class.getName());

        String subClassName = originalTypeName + "_JavassistSummerCompiler_Aop";
        CtClass ctClass = classPool.makeClass(subClassName);
        CtClass superCtClass = JavassistUtil.getCtClass(classPool, originalTypeName);
        JavassistUtil.ctClassSetSuperclass(ctClass, superCtClass);

        CtClass iocContextAwareCtClass = JavassistUtil.getCtClass(classPool, AopType.class.getName());
        ctClass.addInterface(iocContextAwareCtClass);

        addAopTypeMetaField(ctClass);
        addAopTypeMetaGetter(ctClass);

        List<Method> methods = Reflect.getPublicMethods(originalType);
        for (Method method : methods) {
            addOverrideAopMethod(ctClass, method);
        }

        addAopTypeInvokeMethod(ctClass, methods);
        addAopTypeCallMethod(ctClass, methods);

        log.info("compileClass for" + originalTypeName);
        return JavassistUtil.ctClassToClass(ctClass);
    }

    public Class<?> compileReference(BeanDefinition beanDefinition, BeanField beanField) {
        Field field = Reflect.getDeclaredField(beanDefinition.getBeanType(), beanField.getName());

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
            JavassistUtil.ctClassSetSuperclass(ctClass, fieldTypeCtClass);
        }

        CtClass iocContextAwareCtClass = JavassistUtil.getCtClass(classPool, AopType.class.getName());
        ctClass.addInterface(iocContextAwareCtClass);

        addAopTypeMetaField(ctClass);
        addAopTypeMetaGetter(ctClass);

        List<Method> methods = Reflect.getPublicMethods(fieldType);
        for (Method method : methods) {
            addCallDelegateOverrideAopMethod(ctClass, method, beanDefinition, beanField);
        }

        log.info("compileReference beanDefinition.id=" + beanDefinition.getId() + ", " + beanDefinition.getBeanType().getName() + "." + beanField.getName());
        return JavassistUtil.ctClassToClass(ctClass);
    }

    private static void addAopTypeCallMethod(CtClass ctClass, List<Method> methods) {
        String callDelegateOverrideMethodSrc = JavassistSummerCompilerUtil.buildAopTypeCallMethodSrc(methods);

        CtMethod callDelegateOverrideMethod = JavassistUtil.ctNewMethodMake(callDelegateOverrideMethodSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, callDelegateOverrideMethod);
    }

    private static void addAopTypeInvokeMethod(CtClass ctClass, List<Method> methods) {
        String callDelegateOverrideMethodSrc = JavassistSummerCompilerUtil.buildAopTypeInvokeMethodSrc(methods);

        CtMethod callDelegateOverrideMethod = JavassistUtil.ctNewMethodMake(callDelegateOverrideMethodSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, callDelegateOverrideMethod);
    }

    private static void addAopTypeMetaGetter(CtClass ctClass) {
        String iocContextFieldSetterSrc = "public summer.aop.AopTypeMeta getAopTypeMeta() { return this.aopTypeMeta; } ";
        CtMethod iocContextFieldSetter = JavassistUtil.ctNewMethodMake(iocContextFieldSetterSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, iocContextFieldSetter);
    }

    private static void addAopTypeMetaField(CtClass ctClass) {
        CtField ctField = JavassistUtil.ctFieldWithInitMake("private summer.aop.AopTypeMeta aopTypeMeta=new summer.aop.AopTypeMeta(); ", ctClass);
        JavassistUtil.ctClassAddField(ctClass, ctField);
    }

    private static void addCallDelegateOverrideAopMethod(CtClass ctClass, Method method, BeanDefinition beanDefinition, BeanField beanField) {
        String callDelegateOverrideMethodSrc = JavassistSummerCompilerUtil.buildCallDelegateOverrideAopMethodSrc(method, beanDefinition, beanField);
        CtMethod callDelegateOverrideMethod = JavassistUtil.ctNewMethodMake(callDelegateOverrideMethodSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, callDelegateOverrideMethod);
    }

    private static void addOverrideAopMethod(CtClass ctClass, Method method) {
        String overrideMethodSrc = JavassistSummerCompilerUtil.buildOverrideAopMethodSrc(method);
        CtMethod overrideSuperMethod = JavassistUtil.ctNewMethodMake(overrideMethodSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, overrideSuperMethod);
    }
}