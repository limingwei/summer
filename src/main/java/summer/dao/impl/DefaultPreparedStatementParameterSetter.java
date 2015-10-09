package summer.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import summer.dao.PreparedStatementParameterSetter;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:27:33)
 * @since Java7
 */
public class DefaultPreparedStatementParameterSetter implements PreparedStatementParameterSetter {
    public void setParameters(PreparedStatement preparedStatement, Object[] parameters) {
        for (int i = 0; i < parameters.length; i++) {
            try {
                preparedStatement.setObject(i, parameters[i]);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}