package cn.limw.summer.hibernate.delegate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import cn.limw.summer.hibernate.exception.HibernateExceptionUtil;
import cn.limw.summer.hibernate.wrapper.QueryWrapper;
import cn.limw.summer.java.collection.NiceToStringMap;

/**
 * @author li
 * @version 1 (2014年12月24日 上午10:40:29)
 * @since Java7
 */
public class DelegateQuery extends QueryWrapper {
    private static final QueryLogger logger = new QueryLogger();

    private String hql;

    private Map<String, Object> parameters = new NiceToStringMap(new HashMap<String, Object>());

    public DelegateQuery(Query query, String hql) {
        super(query);
        this.hql = hql;
    }

    public String getHql() {
        return hql;
    }

    public Object uniqueResult() {
        try {
            long start = System.currentTimeMillis();
            Object uniqueResult = super.uniqueResult();
            long millis = System.currentTimeMillis() - start;
            logger.log(millis, "DelegateQuery.uniqueResult() hql={}, parameters={}, 耗时: {} ms", hql, parameters, millis);
            return uniqueResult;
        } catch (Exception e) {
            throw new RuntimeException(hql, e);
        }
    }

    public List list() {
        try {
            long start = System.currentTimeMillis();
            List list = super.list();
            long millis = System.currentTimeMillis() - start;
            logger.log(millis, "DelegateQuery.list() hql={}, parameters={}, firstResult={}, maxResults={}, 耗时 {} ms", hql, parameters, getFirstResult(), getMaxResults(), millis);
            return list;
        } catch (Exception e) {
            throw HibernateExceptionUtil.queryListException(e, this);
        }
    }

    public int executeUpdate() {
        try {
            long start = System.currentTimeMillis();
            int executeUpdate = super.executeUpdate();
            long millis = System.currentTimeMillis() - start;
            logger.log(millis, "DelegateQuery.executeUpdate() hql={}, 耗时: {} ms", hql, millis);
            return executeUpdate;
        } catch (Exception e) {
            throw HibernateExceptionUtil.queryExecuteUpdateException(e, this);
        }
    }

    /**
     * @see org.hibernate.engine.query.spi.ParameterMetadata#getNamedParameterDescriptor(String)
     */
    public Query setParameter(String name, Object val) {
        try {
            super.setParameter(name, val);
            parameters.put(name, val);
            return this;
        } catch (Exception e) {
            throw HibernateExceptionUtil.querySetParameterException(name, val, e, this);
        }
    }
}