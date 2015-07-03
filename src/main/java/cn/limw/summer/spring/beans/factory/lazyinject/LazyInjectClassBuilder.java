package cn.limw.summer.spring.beans.factory.lazyinject;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

import org.slf4j.Logger;

import cn.limw.summer.javassist.CtMethodUtil;
import cn.limw.summer.util.ArrayUtil;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年6月19日 上午10:44:49)
 * @since Java7
 */
public class LazyInjectClassBuilder {
    private static final Logger log = Logs.slf4j();

    public static Class<?> buildLazyInjectSubclass(Field lazyInjectField, LazyInject lazyInject) {
        try {
            Class<?> lazyInjectFieldType = lazyInjectField.getType();

            ClassPool classPool = ClassPool.getDefault();

            String lazyInjectFieldTypeName = lazyInjectFieldType.getName();
            CtClass lazyInjectFieldTypeCtClass = classPool.get(lazyInjectFieldTypeName);

            String lazyInjectSubclassName = LazyInjectUtil.buildLazyInjectTypeName(lazyInjectFieldType, classPool);

            CtClass lazyInjectSubCtClass = classPool.makeClass(lazyInjectSubclassName);
            classPool.insertClassPath(new ClassClassPath(lazyInjectFieldType));

            CtClass lazyInjectSupportCtClass = classPool.get(LazyInjectSupport.class.getName());
            if (lazyInjectFieldType.isInterface()) {
                lazyInjectSubCtClass.setInterfaces(new CtClass[] { lazyInjectFieldTypeCtClass, lazyInjectSupportCtClass });
            } else {
                lazyInjectSubCtClass.setInterfaces(new CtClass[] { lazyInjectSupportCtClass });
                lazyInjectSubCtClass.setSuperclass(classPool.get(lazyInjectFieldTypeName));
            }

            addLoggerField(lazyInjectSubCtClass, lazyInjectSubclassName);

            addLazyInjectDelegateHolderFieldAndGetMethod(classPool, lazyInjectSubCtClass);

            CtMethod[] ctMethods = lazyInjectFieldTypeCtClass.getMethods();
            for (CtMethod ctMethod : ctMethods) {
                if (!Object.class.getName().equals(ctMethod.getDeclaringClass().getName()) //
                        && !Modifier.isStatic(ctMethod.getModifiers()) //
                        && !Modifier.isFinal(ctMethod.getModifiers()) //
                        && Modifier.isPublic(ctMethod.getModifiers())) {
                    String delegatedMethodCode = lazyInjectDelegatedMethodCode(ctMethod, lazyInjectFieldTypeName, lazyInject, lazyInjectSubclassName, lazyInjectField);
                    CtMethod newCtMethod = CtNewMethod.make(delegatedMethodCode, lazyInjectSubCtClass);
                    lazyInjectSubCtClass.addMethod(newCtMethod);
                }
            }

            lazyInjectSubCtClass.toBytecode(new java.io.DataOutputStream(cn.limw.summer.util.Files.fileOutputStream("C:\\Users\\li\\Desktop\\out\\" + lazyInjectSubCtClass.getName().replace('.', File.separatorChar) + ".class")));

            return lazyInjectSubCtClass.toClass(); // ctClass 的类型是 javassist.CtNewClass
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void addLazyInjectDelegateHolderFieldAndGetMethod(ClassPool classPool, CtClass lazyInjectSubCtClass) throws NotFoundException, CannotCompileException {
        lazyInjectSubCtClass.addField(CtField.make("private final LazyInjectDelegateHolder _lazyInjectDelegateHolder_ = new LazyInjectDelegateHolder(); ", lazyInjectSubCtClass));
        lazyInjectSubCtClass.addMethod(CtNewMethod.make("/* lazy inject */ public final LazyInjectDelegateHolder _getLazyInjectDelegateHolder_() { " //
                + " return _lazyInjectDelegateHolder_; " //
                + " }", lazyInjectSubCtClass));
    }

    private static void addLoggerField(CtClass ctClass, String className) throws CannotCompileException {
        ctClass.addField(CtField.make(" private static final org.slf4j.Logger log = Logs.slf4j(\"" + className + "\"); ", ctClass));
    }

    private static String lazyInjectDelegatedMethodCode(CtMethod ctMethod, String lazyInjectFieldTypeName, LazyInject lazyInject, String lazyInjectSubclassName, Field lazyInjectField) {
        String returnType = CtMethodUtil.getReturnType(ctMethod).getName();
        String methodName = ctMethod.getName();

        String parameterList = CtMethodUtil.getParameters(ctMethod);
        String exceptions = CtMethodUtil.getExceptions(ctMethod);

        CtClass[] parameterTypes = CtMethodUtil.getParameterTypes(ctMethod);
        String parameterReference = StringUtil.join(", ", CtMethodUtil.$_PARAMETER_PREFIX, "", ArrayUtil.sequence(1, parameterTypes.length));

        String src = " /* lazy inject delegate method */ ";
        src += " public " + returnType + " " + methodName + "(" + parameterList + ") " + exceptions + " { ";//

        src += lazyInjectFieldTypeName + " lazyInjectDelegateTarget = (" + lazyInjectFieldTypeName + ")_getLazyInjectDelegateHolder_().getLazyInjectDelegateTarget();";
        src += " if(null != lazyInjectDelegateTarget) { ";
        src += ("void".equals(returnType) ? "" : " return ") + " lazyInjectDelegateTarget." + methodName + "(" + parameterReference + "); "; // invoke when call
        src += " } else if(LazyInject.RequiredType.WHEN_CALL.equals(_getLazyInjectDelegateHolder_().getLazyInject().required())) { ";
        src += " throw new java.lang.RuntimeException(\"LazyInjectDelegateTarget is null when call, lazyInjectField=" + lazyInjectField + ", lazyInjectSubclassName=" + lazyInjectSubclassName + "\"); "; // exception when call
        src += " } else { ";
        src += " log.warn(\"not calling method " + methodName + "(" + parameterList + "), " + lazyInject + " lazyInjectDelegateTarget of type " + lazyInjectFieldTypeName + " is null\"); "; // log when call
        src += CtMethodUtil.returnNullStatement(returnType);
        src += " } "; // else end
        src += " } "; // method end
        log.debug(src);
        return src;
    }
}