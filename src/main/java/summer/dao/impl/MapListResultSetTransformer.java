package summer.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import summer.dao.ResultSetTransformer;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:35:43)
 * @since Java7
 */
public class MapListResultSetTransformer<K, V> implements ResultSetTransformer<List<Map<K, V>>> {
    public List<Map<K, V>> transform(ResultSet resultSet) {
        return null;
    }
}