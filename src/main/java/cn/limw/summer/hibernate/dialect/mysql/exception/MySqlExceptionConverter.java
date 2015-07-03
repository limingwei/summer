package cn.limw.summer.hibernate.dialect.mysql.exception;

import java.sql.SQLException;

import org.hibernate.JDBCException;
import org.hibernate.exception.spi.SQLExceptionConversionDelegate;
import org.hibernate.exception.spi.SQLExceptionConverter;

/**
 * @author li
 * @version 1 (2015年1月19日 下午1:42:56)
 * @since Java7
 */
public class MySqlExceptionConverter implements SQLExceptionConverter, SQLExceptionConversionDelegate {
    private static final long serialVersionUID = 6907966028084956404L;

    public static final MySqlExceptionConverter INSTANCE = new MySqlExceptionConverter();

    public JDBCException convert(SQLException sqlException, String message, String sql) {
        return new JDBCException(message, sqlException, sql);
    }
}