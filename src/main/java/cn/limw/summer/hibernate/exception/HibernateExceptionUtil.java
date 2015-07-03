package cn.limw.summer.hibernate.exception;

import java.sql.SQLException;

import org.hibernate.JDBCException;
import org.hibernate.QueryException;
import org.hibernate.QueryParameterException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;

import cn.limw.summer.druid.filter.ThreadLocalFilter;
import cn.limw.summer.druid.util.PreparedStatementProxyUtil;
import cn.limw.summer.hibernate.delegate.DelegateQuery;
import cn.limw.summer.hibernate.delegate.DelegateSqlQuery;
import cn.limw.summer.java.sql.exception.TimestampCanNotRepresentRuntimeException;
import cn.limw.summer.mysql.exception.DuplicateEntryRuntimeException;
import cn.limw.summer.util.Logs;

import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * @author li
 * @version 1 (2014年12月24日 上午9:46:11)
 * @since Java7
 */
public class HibernateExceptionUtil {
    private static final Logger log = Logs.slf4j();

    public static Boolean isCouldNotResolveProperty(RuntimeException e) {
        return e instanceof QueryException && e.getMessage().startsWith("could not resolve property:");
    }

    public static String getNotResolvePropertyName(QueryException e) {
        int from = 28; // int from = "could not resolve property: ".length();
        String message = e.getMessage();
        int to = message.indexOf(" ", from);
        return message.substring(from, to);
    }

    public static String getNotResolvePropertyTargetType(QueryException e) {
        String message = e.getMessage();
        int from = 33 + getNotResolvePropertyName(e).length(); // int from = ("could not resolve property: " + getNotResolvePropertyName(e) + " of: ").length();
        int to = message.indexOf(" ", from);
        return message.substring(from, to);
    }

    public static Boolean isForeignKeyConstraintFail(Exception e) {
        String message = getForeignKeyConstraintFailMessage(e);
        return null == message ? false : message.startsWith("Cannot add or update a child row: a foreign key constraint fails");
    }

    public static String getForeignKeyConstraintFailMessage(Exception e) {
        MySQLIntegrityConstraintViolationException mySQLIntegrityConstraintViolationException = getMySQLIntegrityConstraintViolationException(e);
        return null == mySQLIntegrityConstraintViolationException ? null : mySQLIntegrityConstraintViolationException.getMessage();
    }

    public static String getForeignKeyConstraintFailSql(Exception e) {
        ConstraintViolationException constraintViolationException = getConstraintViolationException(e);
        return null == constraintViolationException ? null : constraintViolationException.getSQL();
    }

    public static ConstraintViolationException getConstraintViolationException(Exception e) {
        if (e instanceof DataIntegrityViolationException) {
            DataIntegrityViolationException dataIntegrityViolationException = (DataIntegrityViolationException) e;
            if (dataIntegrityViolationException.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException constraintViolationException = (ConstraintViolationException) dataIntegrityViolationException.getCause();
                return constraintViolationException;
            }
        }
        return null;
    }

    public static Boolean isTimestampCanNotRepresent(Exception e) {
        SQLException sqlException = getSqlException(e);
        return null == sqlException ? false : sqlException.getMessage().contains("can not be represented as java.sql.Timestamp");
    }

    public static SQLException getSqlException(Exception e) {
        if (e instanceof GenericJDBCException) {
            Throwable throwable = e.getCause();
            if (throwable instanceof SQLException) {
                return (SQLException) throwable;
            }
        }
        return null;
    }

    public static String getCanNotRepresentTimestamp(Exception e) {
        SQLException sqlException = getSqlException(e);
        if (null == sqlException) {
            return "[null value]";
        } else {
            String message = sqlException.getMessage();
            int to = message.indexOf("can not be represented as java.sql.Timestamp");
            return message.substring(7, to - 2);
        }
    }

    public static MySQLIntegrityConstraintViolationException getMySQLIntegrityConstraintViolationException(Exception e) {
        if (e instanceof DataIntegrityViolationException) {
            return get_mysql_integrity_constraint_violation_exception_from_data_integrity_violation_exception((DataIntegrityViolationException) e);
        } else if (e instanceof HibernateJdbcException) {
            return get_mysql_integrity_constraint_violation_exception_from_hibernate_jdbc_exception((HibernateJdbcException) e);
        } else if (e instanceof JDBCException) {
            return get_mysql_integrity_constraint_violation_exception_from_jdbc_exception((JDBCException) e);
        }
        return null;
    }

    public static Boolean isDuplicateEntry(Exception e) {
        MySQLIntegrityConstraintViolationException ex = getMySQLIntegrityConstraintViolationException(e);
        return null != ex && ex.getMessage().startsWith("Duplicate entry");
    }

    public static String getDuplicateEntryMessage(Exception e) {
        MySQLIntegrityConstraintViolationException ex = getMySQLIntegrityConstraintViolationException(e);
        return ex.getMessage();
    }

    private static MySQLIntegrityConstraintViolationException get_mysql_integrity_constraint_violation_exception_from_jdbc_exception(JDBCException jdbcException) {
        if (jdbcException.getCause() instanceof MySQLIntegrityConstraintViolationException) {
            return (MySQLIntegrityConstraintViolationException) jdbcException.getCause();
        }
        return null;
    }

    private static MySQLIntegrityConstraintViolationException get_mysql_integrity_constraint_violation_exception_from_hibernate_jdbc_exception(HibernateJdbcException hibernateJdbcException) {
        if (hibernateJdbcException.getCause() instanceof JDBCException) {
            return get_mysql_integrity_constraint_violation_exception_from_jdbc_exception((JDBCException) hibernateJdbcException.getCause());
        }
        return null;
    }

    private static MySQLIntegrityConstraintViolationException get_mysql_integrity_constraint_violation_exception_from_data_integrity_violation_exception(DataIntegrityViolationException dataIntegrityViolationException) {
        if (dataIntegrityViolationException.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) dataIntegrityViolationException.getCause();
            if (constraintViolationException.getCause() instanceof MySQLIntegrityConstraintViolationException) {
                return (MySQLIntegrityConstraintViolationException) constraintViolationException.getCause();
            }
        }
        return null;
    }

    public static boolean isDataTooLong(Exception e) {
        if (e instanceof HibernateJdbcException) {
            MysqlDataTruncation mysqlDataTruncation = get_mysql_data_truncation_from_hibernate_jdbc_exception((HibernateJdbcException) e);
            return null != mysqlDataTruncation && mysqlDataTruncation.getMessage().startsWith("Data truncation: Data too long for column");
        }
        return false;
    }

    public static String getDataTooLongColumn(Exception e) {
        if (e instanceof HibernateJdbcException) {
            MysqlDataTruncation mysqlDataTruncation = get_mysql_data_truncation_from_hibernate_jdbc_exception((HibernateJdbcException) e);
            if (null != mysqlDataTruncation) {
                String message = mysqlDataTruncation.getMessage();
                if (message.startsWith("Data truncation: Data too long for column")) {
                    int beginIndex = message.indexOf('\'');
                    int endIndex = message.indexOf('\'', beginIndex + 1);
                    return message.substring(beginIndex + 1, endIndex);
                }
            }
        }
        return null;
    }

    private static MysqlDataTruncation get_mysql_data_truncation_from_hibernate_jdbc_exception(HibernateJdbcException e) {
        Throwable e1 = e.getCause();
        if (e1 instanceof JDBCException) {
            Throwable e2 = e1.getCause();
            if (e2 instanceof MysqlDataTruncation) {
                return (MysqlDataTruncation) e2;
            }
        }
        return null;
    }

    public static RuntimeException sqlQueryExecuteUpdateException(RuntimeException e) {
        if (HibernateExceptionUtil.isDuplicateEntry(e)) {
            String sql = PreparedStatementProxyUtil.getExecutableSql(ThreadLocalFilter.getPreparedStatementProxy());
            DuplicateEntryRuntimeException duplicateEntryRuntimeException = new DuplicateEntryRuntimeException(HibernateExceptionUtil.getDuplicateEntryMessage(e) + ", sql=" + sql, e);
            return duplicateEntryRuntimeException;
        } else {
            return e;
        }
    }

    public static RuntimeException queryExecuteUpdateException(Exception e, DelegateQuery query) {
        String sql = PreparedStatementProxyUtil.getExecutableSql(ThreadLocalFilter.getPreparedStatementProxy());
        return new RuntimeException("hql=" + query.getHql() + ", sql=" + sql, e);
    }

    public static RuntimeException queryListException(Exception e, DelegateQuery query) {
        if (HibernateExceptionUtil.isTimestampCanNotRepresent(e)) {
            return new TimestampCanNotRepresentRuntimeException(HibernateExceptionUtil.getCanNotRepresentTimestamp(e), e);
        }
        String sql = PreparedStatementProxyUtil.getExecutableSql(ThreadLocalFilter.getPreparedStatementProxy());
        return new RuntimeException("firstResult=" + query.getFirstResult() + ", maxResults=" + query.getMaxResults() + ", hql=" + query.getHql() + ", sql=" + sql + e.getMessage(), e);
    }

    public static RuntimeException querySetParameterException(String name, Object val, Exception e, DelegateQuery query) {
        if (e instanceof QueryParameterException) {
            log.error("setParameter(String, Object), QueryParameterException, " + e.getMessage() + ", hql=" + query.getHql() + ", name=" + name + ", val=" + val);
            return (RuntimeException) e;
        } else {
            String sql = PreparedStatementProxyUtil.getExecutableSql(ThreadLocalFilter.getPreparedStatementProxy());
            return new RuntimeException("hql=" + query.getHql() + ", sql=" + sql, e);
        }
    }

    public static RuntimeException querySetParameterException(String name, Object val, Exception e, DelegateSqlQuery query) {
        if (e instanceof QueryParameterException) {
            log.error("setParameter(String, Object), QueryParameterException, " + e.getMessage() + ", sql=" + query.getSql() + ", name=" + name + ", val=" + val);
            return (RuntimeException) e;
        } else {
            String sql = PreparedStatementProxyUtil.getExecutableSql(ThreadLocalFilter.getPreparedStatementProxy());
            return new RuntimeException("sql=" + sql, e);
        }
    }
}