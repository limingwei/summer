package cn.limw.summer.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2014年11月26日 上午9:52:16)
 * @since Java7
 */
public class ClassPoolUtil {
    public static CtClass get(String className) {
        return get(ClassPool.getDefault(), className);
    }

    public static CtClass get(ClassPool classPool, String className) {
        Mirrors.classForName(className);//如果找不到, 会出这个异常, 否则会出下面的异常
        try {
            return classPool.get(className);
        } catch (NotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}