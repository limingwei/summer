package cn.limw.summer.spring.hibernate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.RootClass;

import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2014年6月25日 上午9:27:41)
 * @since Java7
 */
public class SessionFactoryUtil {
    public static Map<String, String> getComments(Iterator<PersistentClass> iterator) {
        Map<String, String> comments = new HashMap<String, String>();
        while (iterator.hasNext()) {
            PersistentClass persistentClass = (PersistentClass) iterator.next();
            RootClass rootClass = persistentClass.getRootClass();
            Class<?> type = Mirrors.classForName(rootClass.getClassName());
            javax.persistence.Table table = type.getAnnotation(javax.persistence.Table.class);
            Comment comment = type.getAnnotation(Comment.class);
            String tableName = null;
            if (null != comment && null != table) {
                tableName = table.name();
                if (null == tableName || tableName.trim().isEmpty()) {
                    tableName = type.getSimpleName().toLowerCase();
                }
                comments.put(tableName.replace("`", ""), comment.value());
            }
            List<Field> fields = Mirrors.getAllFields(type);
            for (Field field : fields) {
                Comment comment2 = field.getAnnotation(Comment.class);
                if (null != comment2) {
                    addComments(comments, tableName, field, comment2);
                }
            }
        }

        return comments;
    }

    private static void addComments(Map<String, String> comments, String tableName, Field field, Comment comment) {
        JoinTable joinTable = field.getAnnotation(JoinTable.class);
        Id id = field.getAnnotation(Id.class);
        Column column = field.getAnnotation(Column.class);
        JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
        if (null != joinTable) {
            String joinTableName = joinTable.name();
            comments.put(joinTableName, comment.forJoinTable());
            comments.put(joinTableName + "." + joinTable.joinColumns()[0].name(), comment.forJoinColumns()[0]);
            comments.put(joinTableName + "." + joinTable.inverseJoinColumns()[0].name(), comment.forInverseJoinColumns()[0]);
        } else if (null != column) {
            if (null != column.name() && !column.name().trim().isEmpty()) {
                comments.put(tableName + "." + column.name(), comment.value());
            } else {
                comments.put(tableName + "." + field.getName(), comment.value());
            }
        } else if (null != id && null == column) {
            comments.put(tableName + "." + field.getName(), comment.value());
        } else if (null != joinColumn) {
            comments.put(tableName + "." + joinColumn.name(), comment.value());
        }
    }
}