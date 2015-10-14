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

        classPool.importPackage(Method.class.getName());
        classPool.importPackage(AopFilter.class.getName());
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
            addOverrideMethod(ctClass, method);
        }

        addInvokeMethod(ctClass, methods);
        addCallMethod(ctClass, methods);

        log.info("compileClass for" + originalTypeName);
        return JavassistUtil.ctClassToClass(ctClass);
    }

    private void addCallMethod(CtClass ctClass, List<Method> methods) {
        String callDelegateOverrideMethodSrc = "public Object call(String methodSignature, Object[] args){";
        for (Method method : methods) {
            callDelegateOverrideMethodSrc += "if(\"" + JavassistSummerCompilerUtil.getMethodSignature(method) + "\".equals(methodSignature)){";
            if ("void".equals(method.getReturnType().getName())) {
                callDelegateOverrideMethodSrc += "this." + method.getName() + "(" + JavassistSummerCompilerUtil._invoker_arguments(method.getParameterTypes()) + ");";
                callDelegateOverrideMethodSrc += "return null;";
            } else {
                callDelegateOverrideMethodSrc += "return this." + method.getName() + "(" + JavassistSummerCompilerUtil._invoker_arguments(method.getParameterTypes()) + ");";
            }
            callDelegateOverrideMethodSrc += "}";
        }
        callDelegateOverrideMethodSrc += "return null;";
        callDelegateOverrideMethodSrc += "}";
        CtMethod callDelegateOverrideMethod = JavassistUtil.ctNewMethodMake(callDelegateOverrideMethodSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, callDelegateOverrideMethod);
    }

    private void addInvokeMethod(CtClass ctClass, List<Method> methods) {
        String callDelegateOverrideMethodSrc = "public Object invoke(String methodSignature, Object[] args){";
        for (Method method : methods) {
            callDelegateOverrideMethodSrc += "if(\"" + JavassistSummerCompilerUtil.getMethodSignature(method) + "\".equals(methodSignature)){";
            if ("void".equals(method.getReturnType().getName())) {
                callDelegateOverrideMethodSrc += "super." + method.getName() + "(" + JavassistSummerCompilerUtil._invoker_arguments(method.getParameterTypes()) + ");";
                callDelegateOverrideMethodSrc += "return null;";
            } else {
                callDelegateOverrideMethodSrc += "return super." + method.getName() + "(" + JavassistSummerCompilerUtil._invoker_arguments(method.getParameterTypes()) + ");";
            }
            callDelegateOverrideMethodSrc += "}";
        }
        callDelegateOverrideMethodSrc += "return null;";
        callDelegateOverrideMethodSrc += "}";
        CtMethod callDelegateOverrideMethod = JavassistUtil.ctNewMethodMake(callDelegateOverrideMethodSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, callDelegateOverrideMethod);
    }

    private void addAopTypeMetaGetter(CtClass ctClass) {
        String iocContextFieldSetterSrc = "public summer.aop.AopTypeMeta getAopTypeMeta() { return this.aopTypeMeta; } ";
        CtMethod iocContextFieldSetter = JavassistUtil.ctNewMethodMake(iocContextFieldSetterSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, iocContextFieldSetter);
    }

    private void addAopTypeMetaField(CtClass ctClass) {
        CtField ctField = JavassistUtil.ctFieldWithInitMake("private summer.aop.AopTypeMeta aopTypeMeta=new summer.aop.AopTypeMeta(); ", ctClass);
        JavassistUtil.ctClassAddField(ctClass, ctField);
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
            addCallDelegateOverrideMethod(ctClass, method, beanDefinition, beanField);
        }

        log.info("compileReference beanDefinition.id=" + beanDefinition.getId() + ", " + beanDefinition.getBeanType().getName() + "." + beanField.getName());
        return JavassistUtil.ctClassToClass(ctClass);
    }

    private void addCallDelegateOverrideMethod(CtClass ctClass, Method method, BeanDefinition beanDefinition, BeanField beanField) {
        String callDelegateOverrideMethodSrc = JavassistSummerCompilerUtil.buildCallDelegateOverrideMethodSrc(method, beanDefinition, beanField);
        CtMethod callDelegateOverrideMethod = JavassistUtil.ctNewMethodMake(callDelegateOverrideMethodSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, callDelegateOverrideMethod);
    }

    private void addOverrideMethod(CtClass ctClass, Method method) {
        String overrideMethodSrc = JavassistSummerCompilerUtil.buildOverrideMethodSrc(method);
        CtMethod overrideSuperMethod = JavassistUtil.ctNewMethodMake(overrideMethodSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, overrideSuperMethod);
    }
}