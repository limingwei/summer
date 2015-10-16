package summer.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:16:35)
 * @since Java7
 */
public class SummerDao extends AbstractSummerDao {
    private NamedParameterParser namedParameterParser = new NamedParameterParser();

    public List<Map<String, Object>> listMap(String sql, Map<String, Object> params) {
        try {
            Connection connection = getDataSource().getConnection();

            NamedParameterStatement preparedStatement = new NamedParameterStatement(connection, sql, namedParameterParser);

            for (Entry<String, Object> entry : params.entrySet()) {
                preparedStatement.setObject(entry.getKey(), entry.getValue());
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            String[] columnLabels = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnLabels[i] = resultSetMetaData.getColumnLabel(i + 1);
            }

            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<String, Object>();

                for (int i = 0; i < columnCount; i++) {
                    Object value = resultSet.getObject(i + 1);
                    map.put(columnLabels[i], value);
                }

                mapList.add(map);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
            return mapList;
        } catch (Exception e) {
            throw (e instanceof RuntimeException) ? (RuntimeException) e : new RuntimeException(e);
        }
    }
}