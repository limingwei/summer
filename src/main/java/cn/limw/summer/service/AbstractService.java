package cn.limw.summer.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;

import cn.limw.summer.dao.IDao;
import cn.limw.summer.dao.Page;
import cn.limw.summer.entity.CreatedAt;
import cn.limw.summer.entity.Id;
import cn.limw.summer.entity.UpdatedAt;
import cn.limw.summer.spring.context.AbstractApplicationContextAware;
import cn.limw.summer.spring.util.SpringUtil;
import cn.limw.summer.time.Clock;
import cn.limw.summer.util.ArrayUtil;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2015年6月4日 上午8:52:53)
 * @since Java7
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AbstractService<T, ID> extends AbstractApplicationContextAware {
    protected Logger log = Logs.slf4j();

    private IDao<T, ID> dao;

    private Class<T> entityType;

    public synchronized IDao<T, ID> getDao() {
        if (null == dao) {
            dao = fetchDao();
            if (null == dao) {
                throw new RuntimeException("请在子类中覆盖这个方法 getDao()");
            }
        }
        return dao;
    }

    private IDao<T, ID> fetchDao() {
        IDao<T, ID> daoFieldValue = fetchDaoFieldValue();
        return null == daoFieldValue ? fetchDaoBeanByActualTypes() : daoFieldValue;
    }

    private IDao<T, ID> fetchDaoFieldValue() {
        List<Field> fields = Mirrors.getAllFields(this.getClass());
        for (Field field : fields) {
            Object value = Mirrors.getFieldValue(this, field);
            if (null != value) { // 非空的值
                Class<? extends Object> type = value.getClass();
                while (type.getName().contains("$")) { // 处理Aop类型
                    type = type.getSuperclass();
                }

                if (IDao.class.isAssignableFrom(type)) { // 是Dao
                    Type[] actualTypeArguments = Mirrors.getActualTypeArguments(type);
                    Type[] actualTypeArguments2 = Mirrors.getActualTypeArguments(this.getClass());

                    if (Mirrors.equals(actualTypeArguments, actualTypeArguments2)) { // 泛型与Service相同
                        return (IDao<T, ID>) value;
                    }
                }
            }
        }
        return null;
    }

    public IDao fetchDaoBeanByActualTypes() {
        return SpringUtil.getBeanByTypeAndActualTypes(getApplicationContext(), IDao.class, Mirrors.getActualTypeArguments(getClass()));
    }

    public Class<T> getEntityType() {
        if (null == entityType) {
            entityType = (Class<T>) Mirrors.getActualTypeArgument(getClass(), 0);
        }
        return entityType;
    }

    public Integer count() {
        return getDao().count();
    }

    public Integer save(T entity) {
        if (entity instanceof CreatedAt && null == ((CreatedAt) entity).getCreatedAt()) {
            ((CreatedAt) entity).setCreatedAt(Clock.nowTimestamp());
        }
        Boolean success = getDao().save((T) entity);
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

    public T find() {
        return getDao().find();
    }

    public List<T> list(Page page) {
        return getDao().list(page);
    }

    public T findById(ID id) {
        return getDao().findById(id);
    }

    public Boolean save(Collection<T> entities) {
        return getDao().save(entities);
    }

    public Boolean delete(T entity) {
        return getDao().delete(entity);
    }

    public Boolean deleteById(ID id) {
        return 0 <= getDao().deleteByIds(id);
    }

    public Integer saveIgnoreNull(T entity) {
        Boolean success = getDao().saveIgnoreNull((T) entity);
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

    public Boolean update(T entity) {
        if (entity instanceof UpdatedAt && null == ((UpdatedAt) entity).getUpdatedAt()) {
            ((UpdatedAt) entity).setUpdatedAt(Clock.nowTimestamp());
        }
        return getDao().update(entity);
    }

    public Boolean merge(T entity) {
        /* final T merge = */getDao().merge(entity);
        return true;
    }

    public Boolean updateIgnoreNull(T entity) {
        if (entity instanceof UpdatedAt && null == ((UpdatedAt) entity).getUpdatedAt()) {
            ((UpdatedAt) entity).setUpdatedAt(Clock.nowTimestamp());
        }
        return getDao().updateIgnoreNull(entity);
    }

    public Integer updateColumns(Serializable id, Object... columnsAndValues) {
        if (UpdatedAt.class.isAssignableFrom(getEntityType())) {
            columnsAndValues = ArrayUtil.insert(columnsAndValues, "updated_at", Clock.nowTimestamp());
        }
        return getDao().updateColumns(id, columnsAndValues);
    }

    public Integer updateColumns(Serializable[] ids, Object... columnsAndValues) {
        if (UpdatedAt.class.isAssignableFrom(getEntityType())) {
            columnsAndValues = ArrayUtil.insert(columnsAndValues, "updated_at", Clock.nowTimestamp());
        }
        return getDao().updateColumns(ids, columnsAndValues);
    }

    public Integer updateColumnsByColumn(String byColumn, Object byValue, Object... columnsAndValues) {
        if (UpdatedAt.class.isAssignableFrom(getEntityType())) {
            columnsAndValues = ArrayUtil.insert(columnsAndValues, "updated_at", Clock.nowTimestamp());
        }
        return getDao().updateColumnsByColumn(byColumn, byValue, columnsAndValues);
    }

    /**
     * hql更新，当指定的field不为updatedAt时，不会默认更新updatedAt
     * 如需默认更新updatedAt，请使用支持更新多列的方法：updateColumns(Serializable[] ids, Object... columnsAndValues)，注意此方法是sql更新
     */
    public Integer updateField(String field, Object value, ID... ids) {
        return getDao().updateField(field, value, ids);
    }

    /**
     * sql更新，当指定的column不为updated_at时，不会默认更新updated_at
     * 如需默认更新updated_at，请使用支持更新多列的方法：updateColumns(Serializable[] ids, Object... columnsAndValues)
     */
    public Integer updateColumn(String column, Object value, ID... ids) {
        return getDao().updateColumn(column, value, ids);
    }
}