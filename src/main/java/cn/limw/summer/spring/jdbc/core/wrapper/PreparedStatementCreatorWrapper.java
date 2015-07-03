package cn.limw.summer.spring.jdbc.core.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;

/**
 * @author li
 * @version 1 (2014年11月17日 下午3:38:25)
 * @since Java7
 */
public class PreparedStatementCreatorWrapper implements PreparedStatementCreator {
    private PreparedStatementCreator preparedStatementCreator;

    public PreparedStatementCreatorWrapper(PreparedStatementCreator preparedStatementCreator) {
        this.preparedStatementCreator = preparedStatementCreator;
    }

    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        return preparedStatementCreator.createPreparedStatement(connection);
    }
}