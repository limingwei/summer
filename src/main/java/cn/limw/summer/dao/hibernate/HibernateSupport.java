package cn.limw.summer.dao.hibernate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;

import cn.limw.summer.dao.Page;
import cn.limw.summer.hibernate.HibernateUtil;
import cn.limw.summer.util.Mirrors;

/**
 * @author li ( limingwei@mail.com )
 * @version 1 ( 2014年5月16日 下午1:30:39 )
 */
public class HibernateSupport {
    private static final Map<Class<?>, String> TABLE_NAMES = new ConcurrentHashMap<Class<?>, String>();

    private static final Map<String, Class<?>> ENTITY_TYPES = new ConcurrentHashMap<String, Class<?>>();

    private static final Map<Class<?>, String> ID_FIELDS = new ConcurrentHashMap<Class<?>, String>();

    private static final Map<Class<?>, String> ID_COLUMNS = new ConcurrentHashMap<Class<?>, String>();

    private static final Map<Class<?>, Map<String, String>> FIELD_COLUMNS = new ConcurrentHashMap<Class<?>, Map<String, String>>();

    private static final Map<Class<?>, Map<String, String>> COLUMN_FIELDS = new ConcurrentHashMap<Class<?>, Map<String, String>>();

    private static final Map<Class<?>, String[]> FIELDS = new ConcurrentHashMap<Class<?>, String[]>();

    private static final Map<Class<?>, String[]> BASIC_FIELDS = new ConcurrentHashMap<Class<?>, String[]>();

    private static final Map<Class<?>, Map<String, Class<?>>> FIELD_TYPES = new ConcurrentHashMap<Class<?>, Map<String, Class<?>>>();

    private static final Map<Class<?>, Map<String, String>> JOIN_COLUMNS = new ConcurrentHashMap<Class<?>, Map<String, String>>();

    private SessionFactory sessionFactory;

    private HibernateTemplate hibernateTemplate;

    /**
     * 查询单个对象返回后是否evict之
     */
    private Boolean evictAfterFind = false;

    public Boolean getEvictAfterFind() {
        return evictAfterFind;
    }

    public void setEvictAfterFind(Boolean evictAfterFind) {
        this.evictAfterFind = evictAfterFind;
    }

    public SessionFactory getSessionFactory() {
        if (null == sessionFactory && null != getHibernateTemplate()) {
            sessionFactory = getHibernateTemplate().getSessionFactory();
        }
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public HibernateTemplate getHibernateTemplate() {
        if (null == hibernateTemplate && null != getSessionFactory()) {
            hibernateTemplate = new cn.limw.summer.spring.hibernate.HibernateTemplate(getSessionFactory());
        }
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public String getJoinColumn(Class<?> type, String field) {
        Map<String, String> map = JOIN_COLUMNS.get(type);
        if (null == map) {
            JOIN_COLUMNS.put(type, map = new HashMap<String, String>());
        }
        String column = map.get(field);
        if (null == column) {
            map.put(field, column = HibernateUtil.getJoinColumn(type, field));
        }
        return column;
    }

    public Class<?> getFieldType(Class<?> type, String field) {
        Map<String, Class<?>> types = FIELD_TYPES.get(type);
        if (null == types) {
            FIELD_TYPES.put(type, types = new HashMap<String, Class<?>>());
        }
        Class<?> fieldType = types.get(field);
        if (null == fieldType) {
            Field _field = Mirrors.getField(type, field);
            if (null != _field) {
                types.put(field, fieldType = _field.getType());
            }
        }
        return fieldType;
    }

    public String getIdColumn(Class<?> type) {
        String idColumn = ID_COLUMNS.get(type);
        if (null == idColumn) {
            ID_COLUMNS.put(type, idColumn = HibernateUtil.getIdColumn(getSessionFactory(), type));
        }
        return idColumn;
    }

    public String getIdField(Class<?> type) {
        String idField = ID_FIELDS.get(type);
        if (null == idField) {
            ID_FIELDS.put(type, idField = HibernateUtil.getIdField(getSessionFactory(), type));
        }
        return idField;
    }

    public String getTableName(Class<?> type) {
        String tableName = TABLE_NAMES.get(type);
        if (null == tableName) {
            TABLE_NAMES.put(type, tableName = HibernateUtil.getTableName(getSessionFactory(), type));
        }
        return tableName;
    }

    public Class<?> getEntityType(String tableName) {
        Class<?> entityType = ENTITY_TYPES.get(tableName);
        if (null == entityType) {
            ENTITY_TYPES.put(tableName, entityType = HibernateUtil.getEntityType(getSessionFactory(), tableName));
        }
        return entityType;
    }

    public String[] getFields(Class<?> entityType) {
        String[] _fields = FIELDS.get(entityType);
        if (null == _fields) {
            FIELDS.put(entityType, _fields = HibernateUtil.getFields(sessionFactory, entityType));
        }
        return _fields;
    }

    public String[] getBasicFields(Class<?> entityType) {
        String[] _fields = BASIC_FIELDS.get(entityType);
        if (null == _fields) {
            BASIC_FIELDS.put(entityType, _fields = HibernateUtil.getBasicFields(sessionFactory, entityType));
        }
        return _fields;
    }

    /**
     * 根据属性名查列名
     */
    public String getColumn(Class<?> entityType, String field) {
        Map<String, String> _columns = COLUMN_FIELDS.get(entityType);
        if (null == _columns) {
            COLUMN_FIELDS.put(entityType, _columns = new HashMap<String, String>());
        }
        String _column = _columns.get(field);
        if (null == _column) {
            _columns.put(field, _column = HibernateUtil.getColumnByField(getSessionFactory(), entityType, field));
        }
        return _column;
    }

    /**
     * 根据列名查属性名
     */
    public String getField(Class<?> entityType, String column) {
        Map<String, String> _fields = FIELD_COLUMNS.get(entityType);
        if (null == _fields) {
            FIELD_COLUMNS.put(entityType, _fields = new HashMap<String, String>());
        }
        String _field = _fields.get(column);
        if (null == _field) {
            _fields.put(column, _field = HibernateUtil.getFieldByColumn(getSessionFactory(), entityType, column));
        }
        return _field;
    }

    /**
     * getSessionFactory().getCurrentSession()
     */
    public Session getCurrentSession() {
        return HibernateUtil.getCurrentSession(getSessionFactory());
    }

    public SQLQuery createSqlQuery(Session session, String sql, Object[] args) {
        return HibernateUtil.setParameters(session.createSQLQuery(sql), args);
    }

    public Query createQuery(Session session, String hql, Object[] args) {
        return HibernateUtil.setParameters(session.createQuery(hql), args);
    }

    public Query setPage(Query query, Page page) {
        return HibernateUtil.setPage(query, page);
    }
}