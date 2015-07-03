package cn.limw.summer.spring.hibernate;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateCallback;

import cn.limw.summer.druid.filter.ThreadLocalFilter;
import cn.limw.summer.druid.util.PreparedStatementProxyUtil;
import cn.limw.summer.hibernate.exception.HibernateExceptionUtil;
import cn.limw.summer.mysql.exception.DataTooLongRuntimeException;
import cn.limw.summer.mysql.exception.DuplicateEntryRuntimeException;
import cn.limw.summer.mysql.exception.ForeignKeyConstraintFailRuntimeException;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2014年12月24日 上午11:42:15)
 * @since Java7
 */
public class HibernateTemplate extends AbstractHibernateTemplate {
    private static final Logger log = Logs.slf4j();

    public HibernateTemplate() {}

    public HibernateTemplate(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected <T> T doExecute(HibernateCallback<T> action, boolean enforceNativeSession) throws DataAccessException {
        try {
            return super.doExecute(action, enforceNativeSession);
        } catch (Exception e) {
            if (HibernateExceptionUtil.isForeignKeyConstraintFail(e)) {
                String sql = PreparedStatementProxyUtil.getExecutableSql(ThreadLocalFilter.getPreparedStatementProxy());
                String message = HibernateExceptionUtil.getForeignKeyConstraintFailMessage(e);
                throw new ForeignKeyConstraintFailRuntimeException(message + ", sql=" + sql, e);
            } else if (HibernateExceptionUtil.isDuplicateEntry(e)) {
                String sql = PreparedStatementProxyUtil.getExecutableSql(ThreadLocalFilter.getPreparedStatementProxy());
                String message = HibernateExceptionUtil.getDuplicateEntryMessage(e);
                DuplicateEntryRuntimeException duplicateEntryRuntimeException = new DuplicateEntryRuntimeException(message + ", sql=" + sql, e);
                throw duplicateEntryRuntimeException;
            } else if (HibernateExceptionUtil.isDataTooLong(e)) {
                String sql = PreparedStatementProxyUtil.getExecutableSql(ThreadLocalFilter.getPreparedStatementProxy());
                String column = HibernateExceptionUtil.getDataTooLongColumn(e);
                throw new DataTooLongRuntimeException("Data too long for column '" + column + "', sql=" + sql, e);
            } else {
                String sql = PreparedStatementProxyUtil.getExecutableSql(ThreadLocalFilter.getPreparedStatementProxy());
                log.error("doExecute error, sql=" + sql, e);
                throw e;
            }
        }
    }
}