package cn.limw.summer.dao.summer;

import java.io.File;

import javassist.ClassPool;
import javassist.CtClass;

/**
 * @author li
 * @version 1 (2015年6月23日 下午3:30:46)
 * @since Java7
 */
public class QueryBuilderProducer {
    @SuppressWarnings("unchecked")
    public Class<QueryBuilder> produce(Class<?> type) {
        try {
            ClassPool classPool = ClassPool.getDefault();

            CtClass queryBuilderCtClass = classPool.makeClass(type.getName() + "_QueryBuilder");
            queryBuilderCtClass.setInterfaces(new CtClass[] { classPool.get(QueryBuilder.class.getName()) });
            queryBuilderCtClass.toBytecode(new java.io.DataOutputStream(cn.limw.summer.util.Files.fileOutputStream("C:\\Users\\li\\Desktop\\out\\" + queryBuilderCtClass.getName().replace('.', File.separatorChar) + ".class")));
            return queryBuilderCtClass.toClass(); // ctClass 的类型是 javassist.CtNewClass
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}