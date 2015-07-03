package cn.limw.summer.hibernate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.JoinColumn;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Formula;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.slf4j.Logger;
import org.springframework.util.Assert;

import cn.limw.summer.dao.ListWithPage;
import cn.limw.summer.dao.Page;
import cn.limw.summer.hibernate.delegate.DelegateSession;
import cn.limw.summer.hibernate.wrapper.QueryWrapper;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Errors;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Maps;
import cn.limw.summer.util.Mirrors;
import cn.limw.summer.util.StringUtil;

/**
 * @author li ( limingwei@mail.com )
 * @version 1 ( 2014年5月16日 下午5:15:05 )
 */
public class HibernateUtil {
    private static final Logger log = Logs.slf4j();

    /**
     * 传入PageSize<0时不分页
     */
    public static Query setPage(Query query, final Page page) {
        if (null != page && page.getPageSize() >= 0) {
            query.setFirstResult(page.getFrom()).setMaxResults(page.getPageSize());
            return new QueryWrapper(query) {
                public List list() {
                    List list = super.list();
                    return null == list ? null : new ListWithPage(list, page);
                }
            };
        } else {
            return query;
        }
    }

    public static SQLQuery setParameters(SQLQuery query, Object... args) {
        return (SQLQuery) setParameters((Query) query, args);
    }

    public static Query setParameters(Query query, Object... args) {
        for (int i = 0, map_parameter_count = 0; null != args && i < args.length; i++) {
            if (args[i] instanceof Map) {
                query = setParameterMap(query, (Map) args[i]);
                map_parameter_count++;
            } else {
                query.setParameter(i - map_parameter_count, args[i]);
            }
        }
        return query;
    }

    public static SQLQuery setParameterMap(SQLQuery query, Map<String, Object> args) {
        return (SQLQuery) setParameterMap((Query) query, args);
    }

    /**
     * setArgMap
     */
    public static Query setParameterMap(Query query, Map<String, Object> args) {
        for (Entry<String, Object> arg : args.entrySet()) {
            String key = arg.getKey();
            Object value = arg.getValue();
            if (value instanceof Collection) {
                query.setParameterList(key, (Collection) value);
            } else if (null != value && value.getClass().isArray()) {
                Object[] array = (Object[]) value;
                if (null != array && array.length == 1 // 
                        && null != array[0] && array[0].getClass().isArray()) { // 不知为什么前面传入的一纬数组, 进来后变成了二维数组
                    query.setParameterList(key, (Object[]) array[0]);
                } else {
                    query.setParameterList(key, array);
                }
            } else {
                query.setParameter(key, value);
            }
        }
        return query;
    }

    /**
     * 返回 Pojo ID 属性名
     */
    public static String getIdField(SessionFactory sessionFactory, Class<?> entityType) {
        ClassMetadata classMetadata = sessionFactory.getClassMetadata(entityType);
        return Asserts.noNull(classMetadata, "classMetadata 为空 entityType=" + entityType).getIdentifierPropertyName();
    }

    /**
     * 返回 Pojo 对应表的 ID 字段名
     */
    public static String getIdColumn(SessionFactory sessionFactory, Class<?> entityType) {
        ClassMetadata classMetadata = sessionFactory.getClassMetadata(entityType);
        return ((SingleTableEntityPersister) classMetadata).getIdentifierColumnNames()[0];
    }

    /**
     * 返回表名
     */
    public static String getTableName(SessionFactory sessionFactory, Class<?> entityType) {
        ClassMetadata classMetadata = sessionFactory.getClassMetadata(entityType);
        Asserts.noNull(classMetadata, "sessionFactory中无" + entityType + "的classMetadata");
        return ((SingleTableEntityPersister) classMetadata).getTableName();
    }

    /**
     * 根据表名查询对象类型
     */
    public static Class<?> getEntityType(SessionFactory sessionFactory, String tableName) {
        Map<String, ClassMetadata> metas = sessionFactory.getAllClassMetadata();
        for (Entry<String, ClassMetadata> entry : metas.entrySet()) {
            if (entry.getValue() instanceof SingleTableEntityPersister) {
                SingleTableEntityPersister each = (SingleTableEntityPersister) entry.getValue();
                if (tableName.equalsIgnoreCase(each.getTableName())) {
                    return Mirrors.classForName(each.getEntityName());
                }
            }
        }
        return null;
    }

    /**
     * 返回对象对应表的列中列名包含特定字符串的列名称
     */
    public static String getColumn(SessionFactory sessionFactory, Class<?> entityType, String columnName) {
        ClassMetadata classMetadata = sessionFactory.getClassMetadata(entityType);
        Integer propertyNum = classMetadata.getPropertyNames().length;
        for (int i = 0; i < propertyNum; i++) {
            String[] propertyNames = ((SingleTableEntityPersister) classMetadata).getPropertyColumnNames(i);
            for (String name : propertyNames) {
                if (StringUtil.containsIgnoreCase(name, columnName)) {
                    return name;
                }
            }
        }
        return null;
    }

    public static String getFieldByColumn(SessionFactory sessionFactory, Class<?> entityType, String column) {
        ClassMetadata classMetadata = sessionFactory.getClassMetadata(entityType);
        SingleTableEntityPersister singleTableEntityPersister = (SingleTableEntityPersister) classMetadata;
        String[] propertyNames = singleTableEntityPersister.getPropertyNames();
        for (int i = 0; i < propertyNames.length; i++) {
            String[] propertyColumnNames = singleTableEntityPersister.getPropertyColumnNames(i);
            for (String _columnName : propertyColumnNames) {
                if (_columnName.equals(column)) {
                    return propertyNames[i];
                }
            }
        }
        return null;
    }

    public static String getColumnByField(SessionFactory sessionFactory, Class<?> entityType, String field) {
        try {
            ClassMetadata classMetadata = sessionFactory.getClassMetadata(entityType);
            SingleTableEntityPersister singleTableEntityPersister = (SingleTableEntityPersister) classMetadata;
            return singleTableEntityPersister.getPropertyColumnNames(field)[0];
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() + ", " + entityType, e);
        }
    }

    /**
     * 返回对象属性列表中包含特定字符串的属性,不区分大小写
     */
    public static String getField(SessionFactory sessionFactory, Class<?> entityType, String propertyName) {
        ClassMetadata classMetadata = sessionFactory.getClassMetadata(entityType);
        String[] propertyNames = classMetadata.getPropertyNames();
        return StringUtil.searchContains(propertyNames, propertyName, null);
    }

    public static String[] getFields(SessionFactory sessionFactory, Class<?> entityType) {
        return sessionFactory.getClassMetadata(entityType).getPropertyNames();
    }

    public static String[] getBasicFields(SessionFactory sessionFactory, Class<?> entityType) {
        String[] fields = getFields(sessionFactory, entityType);
        List<String> list = new ArrayList<String>();
        for (String fieldName : fields) {
            Field field = Mirrors.getField(entityType, fieldName);
            Class<?> type = field.getType();
            if (Collection.class.isAssignableFrom(type)) {
                //集合类型不要
            } else if (null != field.getAnnotation(Formula.class)) {
                // Formula 属性不要
            } else {
                list.add(fieldName);
            }
        }
        String[] array = list.toArray(new String[0]);
        log.info("getBasicFields sessionFactory={}, entityType={}, return {}", sessionFactory, entityType, Arrays.toString(array));
        return array;
    }

    public static String getJoinColumn(Class<?> type, String fieldName) {
        try {
            return Mirrors.getField(type, fieldName).getAnnotation(JoinColumn.class).name();
        } catch (Exception e) {
            throw new RuntimeException(type + " " + fieldName + " " + e.getMessage(), e);
        }
    }

    public static Map<String, Object> fieldsEqualsValues(String separator, Object... fieldAndValues) {
        Assert.isTrue(null != fieldAndValues && fieldAndValues.length >= 2 && fieldAndValues.length % 2 == 0, "fieldAndValues 必须传入偶数个值");
        Map<String, Object> args = new HashMap<String, Object>();
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(fieldAndValues[0]).append("=:$1 ");
        args.put("$1", fieldAndValues[1]);

        for (int i = 2; i < fieldAndValues.length - 1; i = i + 2) {
            Object field = fieldAndValues[i];
            Object value = fieldAndValues[i + 1];
            int index = (i / 2) + 1;
            stringBuffer.append(separator).append(field).append("=:$").append(index).append(" ");
            args.put("$" + index, value);
        }

        return Maps.toMap("query", stringBuffer.toString(), "args", args);
    }

    public static Session getCurrentSession(SessionFactory sessionFactory) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            String message = e.getMessage();
            if ("No Hibernate Session bound to thread, and configuration does not allow creation of non-transactional one here".equals(message)) {
                log.error("No Hibernate Session bound to thread, and configuration does not allow creation of non-transactional one here", e);
                session = sessionFactory.openSession();
            } else if ("No Session found for current thread".equals(message)) {
                log.error("No Session found for current thread");
                session = sessionFactory.openSession();
            } else {
                throw Errors.wrap(message + " Maybe check your transaction config , maybe ", e);
            }
        }
        return new DelegateSession(session);
    }
}