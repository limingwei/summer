package cn.limw.summer.dao.summer;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cn.limw.summer.util.Mirrors;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年6月23日 下午3:29:28)
 * @since Java7
 */
@SuppressWarnings("unchecked")
public class EntityMakerProducer {
    public <T> Class<EntityMaker<T>> produce(Class<T> type) {
        try {
            ClassPool classPool = ClassPool.getDefault();

            CtClass entityMakerCtClass = classPool.makeClass(type.getName() + "_EntityMaker");
            entityMakerCtClass.setInterfaces(new CtClass[] { classPool.get(EntityMaker.class.getName()) });

            entityMakerCtClass.addMethod(CtMethod.make(toEntityMethodCode(type), entityMakerCtClass));

            entityMakerCtClass.toBytecode(new java.io.DataOutputStream(cn.limw.summer.util.Files.fileOutputStream("C:\\Users\\li\\Desktop\\out\\" + entityMakerCtClass.getName().replace('.', File.separatorChar) + ".class")));
            return entityMakerCtClass.toClass(); // ctClass 的类型是 javassist.CtNewClass
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String toEntityMethodCode(Class<?> type) {
        String src = "public /*" + type.getName() + "*/ java.lang.Object toEntity(java.sql.ResultSet resultSet) { ";//
        src += "if(resultSet.next()){";//
        src += type.getName() + " entity =new " + type.getName() + "_Entity(); ";//

        List<Field> fields = Mirrors.getAllFields(type);
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);
            ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
            if (null != id) {
                src += "entity.set" + StringUtil.upperFirst(field.getName()) + "(" + getValue(field.getType(), field.getName(), "id") + ");";
            }
            if (null != column) {
                src += "entity.set" + StringUtil.upperFirst(field.getName()) + "(" + getValue(field.getType(), field.getName(), column.name()) + ");";
            }
            if (null != manyToOne) {
                EntityPool.INSTANCE.get(field.getType());
                src += field.getType().getName() + "_Entity _" + field.getName() + " = new " + field.getType().getName() + "_Entity(); ";
                src += "_" + field.getName() + ".setId(" + getValue(Integer.class, "id", joinColumn.name()) + "); ";
                src += "entity.set" + StringUtil.upperFirst(field.getName()) + "(_" + field.getName() + "); ";
            }
        }

        src += "return entity;";
        src += " } else {";
        src += " return null;";
        src += " }";
        src += " }";
        System.err.println(src);
        return src;
    }

    private String getValue(Class<?> resultType, String fieldName, String column) {
        String key = StringUtil.isEmpty(column) ? fieldName : column;
        if (Integer.class.equals(resultType)) {
            return "java.lang.Integer.valueOf(resultSet.getInt(\"" + key + "\"))";
        } else if (String.class.equals(resultType)) {
            return "resultSet.getString(\"" + key + "\")";
        } else if (Boolean.class.equals(resultType)) {
            return "java.lang.Boolean.valueOf(resultSet.getBoolean(\"" + key + "\"))";
        } else if (Timestamp.class.equals(resultType)) {
            return "resultSet.getTimestamp(\"" + key + "\")";
        } else {
            return "resultSet.getObject(\"" + key + "\")";
        }
    }
}