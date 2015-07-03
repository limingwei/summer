package cn.limw.summer.hibernate.dialect.mysql;

import org.hibernate.exception.spi.SQLExceptionConversionDelegate;
import org.hibernate.exception.spi.SQLExceptionConverter;

import cn.limw.summer.hibernate.dialect.mysql.exception.MySqlExceptionConverter;

/**
 * @author li
 * @version 1 (2014年11月14日 下午1:31:35)
 * @since Java7
 */
public class MySQLDialect extends AbstractMySQLDialect {
    public String getCrossJoinSeparator() {
        return super.getCrossJoinSeparator();
    }

    public SQLExceptionConverter buildSQLExceptionConverter() {
        return MySqlExceptionConverter.INSTANCE;
    }

    public SQLExceptionConversionDelegate buildSQLExceptionConversionDelegate() {
        return MySqlExceptionConverter.INSTANCE;
    }
}