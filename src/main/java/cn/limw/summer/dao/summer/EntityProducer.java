package cn.limw.summer.dao.summer;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewMethod;

import javax.persistence.Column;

import cn.limw.summer.util.Mirrors;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年6月23日 下午3:29:28)
 * @since Java7
 */
@SuppressWarnings("unchecked")
public class EntityProducer {
    public <T> Class<T> produce(Class<T> type) {
        try {
            ClassPool classPool = ClassPool.getDefault();

            CtClass entityCtClass = classPool.makeClass(type.getName() + "_Entity");
            entityCtClass.setSuperclass(classPool.get(type.getName()));
            entityCtClass.setInterfaces(new CtClass[] { classPool.get(EntityLazyLoaderHolder.class.getName()) });

            addEntityLazyLoaderFieldAndMethod(entityCtClass);

            List<Field> fields = Mirrors.getAllFields(type);
            for (Field field : fields) {
                if (null != field.getAnnotation(Column.class)) {
                    addIfNullThenLazyLoadMethod(field, entityCtClass);
                }
            }

            entityCtClass.toBytecode(new java.io.DataOutputStream(cn.limw.summer.util.Files.fileOutputStream("C:\\Users\\li\\Desktop\\out\\" + entityCtClass.getName().replace('.', File.separatorChar) + ".class")));
            return entityCtClass.toClass(); // ctClass 的类型是 javassist.CtNewClass
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addIfNullThenLazyLoadMethod(Field field, CtClass entityCtClass) {
        try {
            String upperFirst = StringUtil.upperFirst(field.getName());
            String typeName = field.getType().getName();
            String src = "public " + typeName + " get" + upperFirst + "(){";
            src += field.getType().getName() + " super_value=super.get" + upperFirst + "();";
            src += "if(null == super_value&&(!getEntityLazyLoader().loaded())){";
            src += "super.set" + upperFirst + "((" + typeName + ")getEntityLazyLoader().load());";
            src += "}";
            src += "return super_value;}";
            entityCtClass.addMethod(CtNewMethod.make(src, entityCtClass));
        } catch (CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }

    private void addEntityLazyLoaderFieldAndMethod(CtClass entityCtClass) {
        try {
            entityCtClass.addField(CtField.make("private EntityLazyLoader entityLazyLoader=new DefaultEntityLazyLoader();", entityCtClass));

            entityCtClass.addMethod(CtNewMethod.make("public EntityLazyLoader getEntityLazyLoader(){"
                    + "return this.entityLazyLoader;}", entityCtClass));
        } catch (CannotCompileException e) {
            throw new RuntimeException(e);
        }
    }
}