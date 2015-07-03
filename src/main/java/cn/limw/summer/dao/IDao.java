package cn.limw.summer.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author li
 * @version 1 (2014年6月24日 下午12:44:14)
 * @since Java7
 */
public interface IDao<T, ID> {
    public Boolean save(T entity);

    public Integer saveReturnIdentifier(T entity);

    public Boolean update(T entity);

    public Boolean persist(T entity);

    public Boolean updateIgnoreNull(T entity);

    public Boolean saveIgnoreNull(T entity);

    public Boolean saveOrUpdate(T entity);

    public Integer updateField(String field, Object value, ID... ids);

    public Integer updateColumn(String column, Object value, ID... ids);

    public Integer updateColumns(Serializable id, Object... columnsAndValues);

    public Integer updateFields(Serializable id, Object... fieldsAndValues);

    public Integer updateColumns(Serializable[] ids, Object... columnsAndValues);

    public Integer updateColumnsByColumn(String byColumn, Object byValue, Object... columnsAndValues);

    public Integer updateFieldsByField(String byField, Object byValue, Object... fieldsAndValues);

    public T merge(T entity);

    public Integer deleteByIds(ID... ids);

    /**
     * 返回任意一个对象
     */
    public T find();

    public T findById(ID id);

    public T findByColumns(Object... columnsAndValues);

    public List<T> list(Page page);

    public List<T> list(ID... ids);

    public List<T> listByFields(Page page, Object... fieldAndValues);

    public Integer count();

    public Integer countByFields(Object... fieldAndValues);

    /**
     * @param fieldAndValues 属性名值交替
     */
    public T findByFields(Object... fieldAndValues);

    public Integer deleteByFields(Object... fieldAndValues);

    public Boolean save(Collection<T> entities);

    public Boolean delete(T entity);

    public Boolean deleteEntities(Collection<T> entities);

    public Boolean saveOrUpdate(Collection<T> entities);

    public List<Integer> listIdByColumns(Page page, Object... fieldAndValues);
}