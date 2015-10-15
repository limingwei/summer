package summer.ioc.compiler;

import java.lang.reflect.Method;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import summer.aop.AopType;
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
    private static final String AOP_TYPE_NAME_SUFFIX = "_Aop";

    private static final Logger log = Log.slf4j();

    public Class<?> compileClass(Class<?> originalType) {
        String originalTypeName = originalType.getName();

        ClassPool classPool = new ClassPool(true);
        classPool.appendClassPath(new LoaderClassPath(getClass().getClassLoader()));

        String subClassName = originalTypeName + AOP_TYPE_NAME_SUFFIX;
        CtClass ctClass = classPool.makeClass(subClassName);
        CtClass superCtClass = JavassistUtil.getCtClass(classPool, originalTypeName);

        // 传入类型是接口
        if (originalType.isInterface()) {
            ctClass.addInterface(superCtClass);
        } else {
            JavassistUtil.ctClassSetSuperclass(ctClass, superCtClass);
        }

        CtClass iocContextAwareCtClass = JavassistUtil.getCtClass(classPool, AopType.class.getName());
        ctClass.addInterface(iocContextAwareCtClass);

        addAopTypeMetaField(ctClass);
        addAopTypeMetaGetter(ctClass);

        List<Method> methods = Reflect.getPublicMethods(originalType);
        for (Method method : methods) {
            addOverrideAopMethod(ctClass, method);
        }

        // 传入类型是接口
        if (!originalType.isInterface()) {
            addAopTypeInvokeMethod(ctClass, methods);
            addAopTypeCallMethod(ctClass, methods);
        }

        log.info("compileClass for" + originalTypeName);
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

    private static void addOverrideAopMethod(CtClass ctClass, Method method) {
        String overrideMethodSrc = JavassistSummerCompilerUtil.buildOverrideAopMethodSrc(method);
        CtMethod overrideSuperMethod = JavassistUtil.ctNewMethodMake(overrideMethodSrc, ctClass);
        JavassistUtil.ctClassAddMethod(ctClass, overrideSuperMethod);
    }
}