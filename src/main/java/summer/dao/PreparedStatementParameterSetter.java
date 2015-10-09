package summer.dao;

import java.sql.PreparedStatement;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:26:21)
 * @since Java7
 */
public interface PreparedStatementParameterSetter {
    public void setParameters(PreparedStatement preparedStatement, Object[] parameters);
}