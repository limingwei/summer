package cn.limw.summer.spring.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * @author li
 * @version 1 (2014年11月17日 下午3:06:21)
 * @since Java7
 */
public class JustReturnResultSetExtractor implements ResultSetExtractor<ResultSet> {
    public static final JustReturnResultSetExtractor INSTANCE = new JustReturnResultSetExtractor();

    public ResultSet extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        return resultSet;
    }
}