package cn.limw.summer.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.ResultTransformer;

import cn.limw.summer.dao.IDao;
import cn.limw.summer.dao.Page;

/**
 * @author li ( limingwei@mail.com )
 * @version 1 ( 2014年5月16日 下午1:11:11 )
 */
public class AbstractDao<T, ID> extends HibernateSupport implements IDao<T, ID> {
    private HibernateDao hibernateDao;

    private Class<T> entityType;

    public Class<T> getEntityType() {
        if (null == entityType) {
            entityType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return entityType;
    }

    public String getEntityName() {
        return getEntityType().getName();
    }

    public HibernateDao getHibernateDao() {
        if (null == hibernateDao) {
            HibernateDao _hibernateDao = new HibernateDao();
            _hibernateDao.setSessionFactory(getSessionFactory());
            _hibernateDao.setHibernateTemplate(getHibernateTemplate());
            _hibernateDao.setEvictAfterFind(getEvictAfterFind());
            hibernateDao = _hibernateDao;
        }
        return hibernateDao;
    }

    public void setHibernateDao(HibernateDao hibernateDao) {
        this.hibernateDao = hibernateDao;
    }

    protected Integer updateBySql(String sql, Object... args) {
        return getHibernateDao().updateBySql(sql, args);
    }

    protected Integer deleteBySql(String sql, Object... args) {
        return getHibernateDao().deleteBySql(sql, args);
    }

    protected Integer deleteByHql(String hql, Object... args) {
        return getHibernateDao().deleteByHql(hql, args);
    }

    protected T findByHql(String hql, Object... args) {
        return getHibernateDao().findByHql(getEntityType(), hql, args);
    }

    protected T findBySql(String sql, Object... args) {
        return getHibernateDao().findBySql(getEntityType(), sql, args);
    }

    protected Map findMap(String sql, Object... args) {
        return getHibernateDao().findMap(sql, args);
    }

    protected List<T> listByHql(Page page, String hql, Object... args) {
        return getHibernateDao().listByHql(page, hql, args);
    }

    protected Integer countBySql(String sql, Object... args) {
        return getHibernateDao().countBySql(sql, args);
    }

    protected Integer countByHql(String hql, Object... args) {
        return getHibernateDao().countByHql(hql, args);
    }

    protected List<Map> listMap(Page page, final String sql, final Object... args) {
        return getHibernateDao().listMap(page, sql, args);
    }

    protected List<T> listBySql(Page page, String sql, Object... args) {
        return getHibernateDao().listBySql(getEntityType(), page, sql, args);
    }

    protected List<T> listByHql(ResultTransformer transformer, Page page, String hql, Object... args) {
        return getHibernateDao().listByHql(transformer, page, hql, args);
    }

    /**
     * 执行SQL语句返回单个对象
     */
    protected Object queryBySql(String sql, Object... args) {
        return getHibernateDao().queryBySql(sql, args);
    }

    protected Object queryByHql(String hql, Object... args) {
        return getHibernateDao().queryByHql(hql, args);
    }

    protected Integer updateByHql(String hql, Object... args) {
        return getHibernateDao().updateByHql(hql, args);
    }

    protected List listObject(Page page, String sql, Object... args) {
        return getHibernateDao().listObject(page, sql, args);
    }

    //###
    public Integer count() {
        return getHibernateDao().count(getEntityType());
    }

    public Integer countByFields(Object... fieldAndValues) {
        return getHibernateDao().countByFields(getEntityType(), fieldAndValues);
    }

    public Integer countByExample(T entity) {
        return getHibernateDao().countByExample(entity);
    }

    public Integer deleteByIds(ID... ids) {
        return getHibernateDao().deleteByIds(getEntityType(), ids);
    }

    public T find() {
        return getHibernateDao().find(getEntityType());
    }

    public T findByColumns(Object... columnsAndValues) {
        return getHibernateDao().findByColumns(getEntityType(), columnsAndValues);
    }

    public T findById(ID id) {
        return getHibernateDao().findById(getEntityType(), id);
    }

    public List<T> list(ID... ids) {
        return getHibernateDao().list(getEntityType(), ids);
    }

    public List<T> list(Page page) {
        return getHibernateDao().list(getEntityType(), page);
    }

    public List<T> listByFields(Page page, Object... fieldAndValues) {
        return getHibernateDao().listByFields(getEntityType(), page, fieldAndValues);
    }

    public T merge(T entity) {
        return getHibernateDao().merge(entity);
    }

    public Boolean save(T entity) {
        return getHibernateDao().save(entity);
    }

    public Integer updateField(String field, Object value, ID... ids) {
        return getHibernateDao().updateField(getEntityType(), field, value, ids);
    }

    public Integer updateColumn(String column, Object value, ID... ids) {
        return getHibernateDao().updateColumn(getEntityType(), column, value, ids);
    }

    public Integer updateColumns(Serializable id, Object... columnsAndValues) {
        return getHibernateDao().updateColumns(getEntityType(), id, columnsAndValues);
    }

    public Integer updateColumns(Serializable[] ids, Object... columnsAndValues) {
        return getHibernateDao().updateColumns(getEntityType(), ids, columnsAndValues);
    }

    public Integer updateColumnsByColumn(String byColumn, Object byValue, Object... columnsAndValues) {
        return getHibernateDao().updateColumnsByColumn(getEntityType(), byColumn, byValue, columnsAndValues);
    }

    public Integer updateFieldsByField(String byField, Object byValue, Object... fieldsAndValues) {
        return getHibernateDao().updateFieldsByField(getEntityType(), byField, byValue, fieldsAndValues);
    }

    public Boolean update(T entity) {
        return getHibernateDao().update(entity);
    }

    public Boolean persist(T entity) {
        return getHibernateDao().persist(entity);
    }

    public Boolean saveOrUpdate(T entity) {
        return getHibernateDao().saveOrUpdate(entity);
    }

    public Boolean updateIgnoreNull(T entity) {
        return getHibernateDao().updateIgnoreNull(entity);
    }

    public Boolean saveIgnoreNull(T entity) {
        return getHibernateDao().saveIgnoreNull(entity);
    }

    public T findByExample(T entity) {
        return getHibernateDao().findByExample(entity);
    }

    public T findByFields(Object... fieldAndValues) {
        return getHibernateDao().findByFields(getEntityType(), fieldAndValues);
    }

    public Integer deleteByFields(Object... fieldAndValues) {
        return getHibernateDao().deleteByFields(getEntityType(), fieldAndValues);
    }

    public Boolean save(Collection<T> entities) {
        return getHibernateDao().save(entities);
    }

    public Boolean deleteEntities(Collection<T> entities) {
        return getHibernateDao().delete(entities);
    }

    public Boolean delete(T entity) {
        return getHibernateDao().delete(entity);
    }

    public Boolean saveOrUpdate(Collection<T> entities) {
        return getHibernateDao().saveOrUpdate(entities);
    }

    public List<Integer> listIdByColumns(Page page, Object... fieldAndValues) {
        return getHibernateDao().listIdByColumns(getEntityType(), page, fieldAndValues);
    }

    public Integer saveReturnIdentifier(T entity) {
        return getHibernateDao().saveReturnIdentifier(entity);
    }

    public Integer updateFields(Serializable id, Object... fieldsAndValues) {
        return getHibernateDao().updateFields(getEntityType(), id, fieldsAndValues);
    }
}