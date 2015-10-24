package summer.dao.result;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import summer.dao.ResultSetTransformer;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:35:43)
 * @since Java7
 */
public class MapListResultSetTransformer implements ResultSetTransformer<List<Map<String, Object>>> {
    public List<Map<String, Object>> transform(ResultSet resultSet) {
        try {
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
            return mapList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}