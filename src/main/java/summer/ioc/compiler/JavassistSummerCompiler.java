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
        CtClass ctClass = classPool.makeClass(originalTypeName + "$JavassistSummerCompiler");
        CtClass superCtClass = Javassist.getCtClass(classPool, originalTypeName);
        Javassist.setSuperclass(ctClass, superCtClass);

        List<Method> methods = CompilerUtil.getOriginalPublicMethods(originalType);
        for (Method method : methods) {
            String callSuperMethodSrc = JavassistSummerCompilerUtil.makeCallSuperMethodSrc(method);
            CtMethod callSuperMethod = Javassist.ctNewMethodMake(callSuperMethodSrc, ctClass);
            Javassist.ctClassAddMethod(ctClass, callSuperMethod);

            String overrideMethodSrc = JavassistSummerCompilerUtil.makeOverrideMethodSrc(method);
            CtMethod overrideSuperMethod = Javassist.ctNewMethodMake(overrideMethodSrc, ctClass);
            Javassist.ctClassAddMethod(ctClass, overrideSuperMethod);
        }

        Javassist.ctClassToBytecode(ctClass, Stream.newFileOutputStream("/classes/a.class"));
        return Javassist.ctClassToClass(ctClass);
    }
}