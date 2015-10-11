package summer.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

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
                Object value = parameters[i];
                if (null == value) {
                    preparedStatement.setNull(i, Types.NULL); // TODO 类型不对
                } else if (value instanceof Integer) {
                    preparedStatement.setInt(i, ((Integer) value).intValue());
                } else if (int.class.equals(value.getClass())) {
                    preparedStatement.setInt(i, (int) value);
                } else {
                    preparedStatement.setObject(i, value);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}