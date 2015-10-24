package summer.dao.util;

import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import summer.dao.statement.NamedParameterStatement;

/**
 * @author li
 * @version 1 (2015年10月24日 下午6:18:00)
 * @since Java7
 */
public class NamedParameterStatementUtil {
    public static NamedParameterStatement setParameters(NamedParameterStatement preparedStatement, Map<String, Object> params) {
        try {
            for (Entry<String, Object> entry : params.entrySet()) {
                preparedStatement.setObject(entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return preparedStatement;
    }
}