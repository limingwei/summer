package cn.limw.summer.hibernate.delegate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import cn.limw.summer.hibernate.exception.HibernateExceptionUtil;
import cn.limw.summer.hibernate.wrapper.SqlQueryWrapper;
import cn.limw.summer.java.collection.NiceToStringMap;

/**
 * @author li
 * @version 1 (2015年1月26日 下午5:15:31)
 * @since Java7
 */
public class DelegateSqlQuery extends SqlQueryWrapper {
    private static final QueryLogger logger = new QueryLogger();

    private String sql;

    private Map<String, Object> parameters = new NiceToStringMap(new HashMap<String, Object>());

    public DelegateSqlQuery(SQLQuery sqlQuery, String sql) {
        super(sqlQuery);
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public int executeUpdate() {
        try {
            long start = System.currentTimeMillis();
            int executeUpdate = super.executeUpdate();
            long millis = System.currentTimeMillis() - start;
            logger.log(millis, "DelegateSqlQuery.executeUpdate() sql={}, 耗时: {} ms", sql, millis);
            return executeUpdate;
        } catch (RuntimeException e) {
            throw HibernateExceptionUtil.sqlQueryExecuteUpdateException(e);
        }
    }

    public Object uniqueResult() {
        try {
            long start = System.currentTimeMillis();
            Object uniqueResult = super.uniqueResult();
            long millis = System.currentTimeMillis() - start;
            logger.log(millis, "DelegateSqlQuery.uniqueResult() sql={}, 耗时: {} ms", sql, millis);
            return uniqueResult;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() + ", sql= " + sql + ", parameters=" + parameters, e);
        }
    }

    public List list() {
        try {
            long start = System.currentTimeMillis();
            List list = super.list();
            long millis = System.currentTimeMillis() - start;
            logger.log(millis, "DelegateSqlQuery.list() sql={}, 耗时: {} ms", sql, millis);
            return list;
        } catch (Throwable e) {
            throw new RuntimeException("sql=" + getSql() + ", " + e, e);
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