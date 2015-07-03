package cn.limw.summer.hibernate.delegate;

import java.io.Serializable;

import org.hibernate.Query;
import org.hibernate.QueryException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.limw.summer.hibernate.exception.HibernateExceptionUtil;
import cn.limw.summer.hibernate.wrapper.SessionWrapper;
import cn.limw.summer.java.lang.exception.NoSuchFieldRuntimeException;
import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年12月24日 上午10:35:15)
 * @since Java7
 */
public class DelegateSession extends SessionWrapper {
    private static final long serialVersionUID = -5729322977870693016L;

    public DelegateSession(Session session) {
        super(session);
    }

    public Query createQuery(String hql) {
        try {
            Query query = super.createQuery(hql);
            return new DelegateQuery(query, hql);
        } catch (RuntimeException e) {
            if (HibernateExceptionUtil.isCouldNotResolveProperty(e)) {
                throw new NoSuchFieldRuntimeException(HibernateExceptionUtil.getNotResolvePropertyTargetType((QueryException) e), HibernateExceptionUtil.getNotResolvePropertyName((QueryException) e), e);
            } else {
                throw e;
            }
        }
    }

    public SQLQuery createSQLQuery(String sql) {
        SQLQuery sqlQuery = super.createSQLQuery(Asserts.noEmpty(sql, "sql 不可以为空"));
        return new DelegateSqlQuery(sqlQuery, sql);
    }

    public Serializable save(Object object) {
        return super.save(object);
    }
}