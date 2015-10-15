package summer.util;

import java.io.DataOutputStream;
import java.io.OutputStream;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.compiler.Javac.CtFieldWithInit;

/**
 * @author li
 * @version 1 (2015年10月11日 上午9:56:59)
 * @since Java7
 */
public class JavassistUtil {
    public static Class<?> ctClassToClass(CtClass ctClass) {
        try {
            ctClassToBytecode(ctClass, Stream.newFileOutputStream("/classes/" + ctClass.getName() + ".class"));

            return ctClass.toClass();
        } catch (CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ctClassSetSuperclass(CtClass ctClass, CtClass superCtClass) {
        try {
            ctClass.setSuperclass(superCtClass);
        } catch (CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }

    public static CtClass getCtClass(ClassPool classPool, String originalTypeName) {
        try {
            return classPool.get(originalTypeName);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ctClassToBytecode(CtClass ctClass, OutputStream outputStream) {
        try {
            ctClass.toBytecode(new DataOutputStream(outputStream));
        } catch (Throwable e) {
            throw (e instanceof RuntimeException) ? (RuntimeException) e : new RuntimeException(e);
        }
    }

    public static CtMethod ctNewMethodMake(String src, CtClass declaring) {
        try {
            return CtNewMethod.make(src, declaring);
        } catch (CannotCompileException e) {
            throw new RuntimeException("src=" + src, e);
        }
    }

    public static void ctClassAddMethod(CtClass ctClass, CtMethod ctNewMethodMake) {
        try {
            ctClass.addMethod(ctNewMethodMake);
        } catch (CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }

    public static CtField ctFieldWithInitMake(String src, CtClass declaring) {
        try {
            return CtFieldWithInit.make(src, declaring);
        } catch (CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ctClassAddField(CtClass ctClass, CtField ctField) {
        try {
            ctClass.addField(ctField);
        } catch (CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }
}