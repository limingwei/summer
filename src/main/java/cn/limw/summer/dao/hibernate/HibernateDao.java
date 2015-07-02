package cn.limw.summer.dao.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.ewei.support.dao.Page;
import com.ewei.support.druid.filter.ThreadLocalFilter;
import com.ewei.support.entity.CreatedAt;
import com.ewei.support.entity.Id;
import com.ewei.support.freemarker.util.FreeMarkerUtil;
import com.ewei.support.hibernate.HibernateUtil;
import com.ewei.support.java.sql.util.PreparedStatementUtil;
import com.ewei.support.time.Clock;
import com.ewei.support.util.Asserts;
import com.ewei.support.util.Files;
import com.ewei.support.util.Logs;
import com.ewei.support.util.Maps;
import com.ewei.support.util.Nums;
import com.ewei.support.util.StringUtil;

import freemarker.template.Template;

/**
 * cn.infocare.support.hibernate.HibernateDao ( itsm )
 * @author li ( limingwei@mail.com )
 * @version 1 ( 2014年5月16日 上午11:48:18 )
 */
public class HibernateDao extends HibernateSupport {
    private static final Logger log = Logs.slf4j();

    private static final Template DYNAMIC_UPDATE_SQL_META_TEMPLATE = FreeMarkerUtil.getTemplate(Files.classPathRead("hibernate-template/dynamic-update.htm"));

    private static final Template DYNAMIC_SAVE_SQL_META_TEMPLATE = FreeMarkerUtil.getTemplate(Files.classPathRead("hibernate-template/dynamic-save.htm"));

    private static final Template COUNT_BY_EXAMPLE_SQL_META_TEMPLATE = FreeMarkerUtil.getTemplate(Files.classPathRead("hibernate-template/count_by_example.htm"));

    private static final Map<Class<?>, Template> COUNT_BY_EXAMPLE_SQL_TEMPLATES = new ConcurrentHashMap<Class<?>, Template>();

    private static final Map<Class<?>, Template> DYNAMIC_UPDATE_SQL_TEMPLATES = new ConcurrentHashMap<Class<?>, Template>();

    private static final Map<Class<?>, Template> DYNAMIC_SAVE_SQL_TEMPLATES = new ConcurrentHashMap<Class<?>, Template>();

    public <T> Integer countByExample(T entity) {
        Template template = COUNT_BY_EXAMPLE_SQL_TEMPLATES.get(Asserts.noNull(entity, "entity为空").getClass());
        if (null == template) {
            String source = FreeMarkerUtil.merge(COUNT_BY_EXAMPLE_SQL_META_TEMPLATE, Maps.toMap("dollar", "$", "pound", "#", "at", "@", "type", entity.getClass(), "dao", this));
            COUNT_BY_EXAMPLE_SQL_TEMPLATES.put(entity.getClass(), template = FreeMarkerUtil.getTemplate(source));
            log.info(source);
        }
        String sql = FreeMarkerUtil.merge(template, Maps.toMap("entity", entity));
        return countBySql(sql);
    }

    public <T> Boolean updateIgnoreNull(T entity) {
        Template template = DYNAMIC_UPDATE_SQL_TEMPLATES.get(Asserts.noNull(entity, "entity为空").getClass());
        if (null == template) {
            String source = FreeMarkerUtil.merge(DYNAMIC_UPDATE_SQL_META_TEMPLATE, Maps.toMap("dollar", "$", "pound", "#", "at", "@", "type", entity.getClass(), "dao", this));
            DYNAMIC_UPDATE_SQL_TEMPLATES.put(entity.getClass(), template = FreeMarkerUtil.getTemplate(source));
            log.info(source);
        }
        String sql = FreeMarkerUtil.merge(template, Maps.toMap("entity", entity));
        return 0 < updateBySql(sql);
    }

    public <T> Boolean saveIgnoreNull(T entity) {
        Template template = DYNAMIC_SAVE_SQL_TEMPLATES.get(Asserts.noNull(entity, "entity为空").getClass());
        if (null == template) {
            String source = FreeMarkerUtil.merge(DYNAMIC_SAVE_SQL_META_TEMPLATE, Maps.toMap("dollar", "$", "pound", "#", "at", "@", "type", entity.getClass(), "dao", this));
            DYNAMIC_SAVE_SQL_TEMPLATES.put(entity.getClass(), template = FreeMarkerUtil.getTemplate(source));
            log.info(source);
        }
        String sql = FreeMarkerUtil.merge(template, Maps.toMap("entity", entity));
        Boolean ret = 0 < updateBySql(sql);

        if (entity instanceof Id) {
            Integer generatedKey = PreparedStatementUtil.getGeneratedKey(ThreadLocalFilter.getPreparedStatementProxy());
            if (null == ((Id) entity).getId() && null != generatedKey) {
                ((Id) entity).setId(generatedKey); // 如何返回新产生的ID
            }
        }
        return ret;
    }

    public <T> T evict(T entity) {
        if (null != entity) {
            getCurrentSession().evict(entity);
        }
        return entity;
    }

    public <T> T findByColumns(Class<T> type, Object... columnsAndValues) {
        Map<String, Object> fieldsEqualsValues = HibernateUtil.fieldsEqualsValues(" AND ", columnsAndValues);
        String hql = " SELECT * FROM " + getTableName(type) + " WHERE " + fieldsEqualsValues.get("query");
        T entity = findBySql(type, hql, fieldsEqualsValues.get("args"));
        return getEvictAfterFind() ? evict(entity) : entity;
    }

    public <T> T findByFields(Class<T> type, Object... fieldAndValues) {
        Map<String, Object> fieldsEqualsValues = HibernateUtil.fieldsEqualsValues(" AND ", fieldAndValues);
        String hql = " FROM " + type.getName() + " WHERE " + fieldsEqualsValues.get("query");
        T entity = findByHql(type, hql, fieldsEqualsValues.get("args"));
        return getEvictAfterFind() ? evict(entity) : entity;
    }

    public <T> T findByExample(T entity) {
        List<T> list = getHibernateTemplate().findByExample(entity);
        T result = null == list || list.isEmpty() ? null : list.get(0);
        return getEvictAfterFind() ? evict(result) : result;
    }

    protected static void prepareCriteria(HibernateTemplate hibernateTemplate, Criteria criteria) {
        if (hibernateTemplate.isCacheQueries()) {
            criteria.setCacheable(true);
            if (hibernateTemplate.getQueryCacheRegion() != null) {
                criteria.setCacheRegion(hibernateTemplate.getQueryCacheRegion());
            }
        }
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.getResource(hibernateTemplate.getSessionFactory());
        if (sessionHolder != null && sessionHolder.hasTimeout()) {
            criteria.setTimeout(sessionHolder.getTimeToLiveInSeconds());
        }
    }

    public <T> T findById(Class<T> type, Object id) {
        T entity = findByHql(type, " FROM " + type.getName() + " WHERE " + getIdField(type) + "=? ", id);
        return getEvictAfterFind() ? evict(entity) : entity;
    }

    public <T> T find(Class<T> type) {
        T entity = findByHql(type, " FROM " + type.getName());
        return getEvictAfterFind() ? evict(entity) : entity;
    }

    public <T> T findBySql(Class<T> type, String sql, Object... args) {
        T entity = (T) setPage(createSqlQuery(getCurrentSession(), sql, args), Page.ONE).setResultTransformer(Transformers.aliasToBean(type)).uniqueResult();
        return getEvictAfterFind() ? evict(entity) : entity;
    }

    public <T> T findByHql(Class<T> type, String hql, Object... args) {
        T entity = (T) setPage(createQuery(getCurrentSession(), hql, args), Page.ONE).uniqueResult();
        return getEvictAfterFind() ? evict(entity) : entity;
    }

    public Integer count(Class<?> type) {
        return countBySql(" SELECT COUNT(*) FROM " + getTableName(type));
    }

    public Integer countByHql(String hql, Object... args) {
        return Nums.toInt(createQuery(getCurrentSession(), hql, args).uniqueResult());
    }

    public Integer countBySql(String sql, Object... args) {
        return Nums.toInt(createSqlQuery(getCurrentSession(), sql, args).uniqueResult());
    }

    public <T> Boolean delete(T entity) {
        getHibernateTemplate().delete(entity);
        return true;
    }

    public <T> Boolean delete(Collection<T> entities) {
        for (T entity : entities) {
            delete(entity);
        }
        return true;
    }

    public Integer deleteByColumn(Class<?> type, String columnName, Object value) {
        return this.deleteBySql(" DELETE FROM " + getTableName(type) + " WHERE " + columnName + "=?", value);
    }

    public Integer deleteByField(Class<?> type, String fieldName, Object value) {
        return this.deleteByHql(" DELETE FROM " + type.getName() + " WHERE " + fieldName + "=?", value);
    }

    public Integer deleteByHql(String hql, Object... args) {
        return createQuery(getCurrentSession(), hql, args).executeUpdate();
    }

    public Integer deleteByIds(Class<?> type, Serializable... ids) {
        return deleteByHql(" DELETE FROM " + type.getName() + " WHERE " + getIdField(type) + " IN (" + StringUtil.join(",", ids) + ")");
    }

    public Integer deleteBySql(String sql, Object... args) {
        return createSqlQuery(getCurrentSession(), sql, args).executeUpdate();
    }

    public Map findMap(String sql, Object[] args) {
        return (Map) createSqlQuery(getCurrentSession(), sql, args).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
    }

    public <T> List<T> list(Class<T> type) {
        return (List<T>) getHibernateTemplate().find(" FROM " + type.getName());
    }

    public <T> List<T> list(Class<T> type, Page page) {
        return listByHql(page, " FROM " + type.getName());
    }

    public <T> List<T> list(Class<T> type, Serializable... ids) {
        return listByHql(Page.MAX, " FROM " + type.getName() + " WHERE " + getIdField(type) + " IN (" + StringUtil.join("", ids) + ")");
    }

    public <T> List<T> listByHql(Page page, String hql, Object... args) {
        return setPage(createQuery(getCurrentSession(), hql, args), page).list();
    }

    public <T> List<T> listByHql(ResultTransformer transformer, Page page, String hql, Object[] args) {
        return setPage(createQuery(getCurrentSession(), hql, args), page).setResultTransformer(transformer).list();
    }

    public <T> List<T> listBySql(Class<T> type, Page page, String sql, Object... args) {
        return setPage(createSqlQuery(getCurrentSession(), sql, args).addEntity(type), page).list();
    }

    public List<Integer> listInt(Page page, String sql, Object... args) {
        return setPage(createSqlQuery(getCurrentSession(), sql, args), page).setResultTransformer(CriteriaSpecification.PROJECTION).list();// 未使用缓存
    }

    public List<Map> listMap(Page page, String sql, Object... args) {
        return setPage(createSqlQuery(getCurrentSession(), sql, args), page).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    public List listObject(Page page, String sql, Object... args) {
        return setPage(createSqlQuery(getCurrentSession(), sql, args), page).list();
    }

    public <T> T merge(T entity) {
        return getHibernateTemplate().merge(entity);
    }

    public Object queryByHql(String hql, Object[] args) {
        return createQuery(getCurrentSession(), hql, args).uniqueResult();
    }

    /**
     * 执行SQL语句返回单个对象
     */
    public Object queryBySql(String sql, Object[] args) {
        return createSqlQuery(getCurrentSession(), sql, args).uniqueResult();
    }

    public <T> Boolean save(Collection<T> entities) {
        if (null != entities) {
            for (T entity : entities) {
                save(entity);
            }
        }
        return true;
    }

    public <T> Integer saveReturnIdentifier(T entity) {
        if (entity instanceof CreatedAt && null == ((CreatedAt) entity).getCreatedAt()) {
            ((CreatedAt) entity).setCreatedAt(Clock.nowTimestamp());
        }
        Boolean success = save(entity);
        if (success) {
            if (entity instanceof Id) {
                return ((Id) entity).getId();
            } else {
                log.info(entity.getClass().getName() + " is not subclass of Id");
                return null;
            }
        } else {
            return null;
        }
    }

    public <T> Boolean save(T entity) {
        getHibernateTemplate().save(entity);
        return true;
    }

    public <T> Boolean saveOrUpdate(Collection<T> entities) {
        for (T entity : entities) {
            getHibernateTemplate().saveOrUpdate(entity);
        }
        return true;
    }

    public <T> Boolean saveOrUpdate(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
        return true;
    }

    public <T> Boolean update(Collection<T> entities) {
        if (null != entities) {
            for (T entity : entities) {
                update(entity);
            }
        }
        return true;
    }

    public <T> Boolean update(T entity) {
        getHibernateTemplate().update(entity);
        return true;
    }

    public <T> Boolean persist(T entity) {
        getHibernateTemplate().persist(entity);
        return true;
    }

    public Integer updateByHql(String hql, Object... args) {
        return createQuery(getCurrentSession(), hql, args).executeUpdate();
    }

    public Integer updateBySql(String sql, Object... args) {
        return createSqlQuery(getCurrentSession(), sql, args).executeUpdate();
    }

    public Integer updateColumn(Class<?> type, String column, Object value, Serializable... ids) {
        Asserts.noNull(getField(type, column), "传入的 column=" + column + " 应为类型 " + type + " 对应的表的一个字段");
        String sql = "UPDATE " + getTableName(type) + " SET " + column + "=:value WHERE " + getIdColumn(type) + " IN (:ids) ";
        Map args = Maps.toMap("value", value, "ids", ids);
        return updateBySql(sql, args);
    }

    public Integer updateField(Class<?> type, String field, Object value, Serializable... ids) {
        Asserts.noNull(getColumn(type, field), "传入的 field=" + field + " 应为类型 " + type + " 的一个属性");
        String hql = "UPDATE " + type.getName() + " SET " + field + "=:value WHERE " + getIdField(type) + " IN (:ids) ";
        Map args = Maps.toMap("value", value, "ids", ids);
        return updateByHql(hql, args);
    }

    public Integer updateColumns(Class<?> type, Serializable id, Object... columnsAndValues) {
        return updateColumnsByColumn(type, getIdColumn(type), id, columnsAndValues);
    }

    public Integer updateFields(Class<?> type, Serializable id, Object... columnsAndValues) {
        return updateFieldsByField(type, getIdColumn(type), id, columnsAndValues);
    }

    public Integer updateColumns(Class<?> type, Serializable[] ids, Object... columnsAndValues) {
        Map<String, Object> fieldsEqualsValues = HibernateUtil.fieldsEqualsValues(", ", columnsAndValues);
        String sql = " UPDATE " + getTableName(type) + " SET " + fieldsEqualsValues.get("query") + " WHERE " + getIdField(type) + " IN (:ids) ";
        Map<String, Object> args = (Map<String, Object>) fieldsEqualsValues.get("args");
        args.put("ids", ids);
        return updateBySql(sql, args);
    }

    public Integer updateColumnsByColumn(Class<?> type, String byColumn, Object byValue, Object... columnsAndValues) {
        Map<String, Object> fieldsEqualsValues = HibernateUtil.fieldsEqualsValues(", ", columnsAndValues);
        String sql = " UPDATE " + getTableName(type) + " SET " + fieldsEqualsValues.get("query") + " WHERE " + byColumn + "=:arg_by_column ";
        Map<String, Object> args = (Map<String, Object>) fieldsEqualsValues.get("args");
        args.put("arg_by_column", byValue);
        return updateBySql(sql, args);
    }

    public Integer updateFieldsByField(Class<?> type, String byField, Object byValue, Object... fieldsAndValues) {
        Map<String, Object> fieldsEqualsValues = HibernateUtil.fieldsEqualsValues(", ", fieldsAndValues);
        String sql = " UPDATE " + type.getName() + " SET " + fieldsEqualsValues.get("query") + " WHERE " + byField + "=:arg_by_column ";
        Map<String, Object> args = (Map<String, Object>) fieldsEqualsValues.get("args");
        args.put("arg_by_column", byValue);
        return updateByHql(sql, args);
    }

    public <T> List<T> listByFields(Class<T> type, Page page, Object[] fieldAndValues) {
        Map<String, Object> fieldsEqualsValues = HibernateUtil.fieldsEqualsValues(" AND ", fieldAndValues);
        String hql = " FROM " + type.getName() + " WHERE " + fieldsEqualsValues.get("query");
        return listByHql(page, hql, fieldsEqualsValues.get("args"));
    }

    public Integer deleteByFields(Class<?> type, Object... fieldAndValues) {
        Map<String, Object> fieldsEqualsValues = HibernateUtil.fieldsEqualsValues(" AND ", fieldAndValues);
        String hql = " DELETE " + type.getName() + " WHERE " + fieldsEqualsValues.get("query");
        return deleteByHql(hql, fieldsEqualsValues.get("args"));
    }

    public Integer countByFields(Class<?> type, Object... fieldAndValues) {
        Map<String, Object> fieldsEqualsValues = HibernateUtil.fieldsEqualsValues(" AND ", fieldAndValues);
        String hql = " SELECT COUNT(*) FROM " + type.getName() + " WHERE " + fieldsEqualsValues.get("query");
        return countByHql(hql, fieldsEqualsValues.get("args"));
    }

    public List<Integer> listIdByColumns(Class<?> type, Page page, Object[] fieldAndValues) {
        Map<String, Object> fieldsEqualsValues = HibernateUtil.fieldsEqualsValues(" AND ", fieldAndValues);
        String sql = " SELECT " + getIdColumn(type) + " FROM " + getTableName(type) + " WHERE " + fieldsEqualsValues.get("query");
        return listInt(page, sql, fieldsEqualsValues.get("args"));
    }
}