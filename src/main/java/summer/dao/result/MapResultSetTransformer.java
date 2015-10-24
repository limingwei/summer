package summer.dao.result;

import java.sql.ResultSet;
import java.util.Map;

import summer.dao.ResultSetTransformer;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:32:17)
 * @since Java7
 */
public class MapResultSetTransformer<K, V> implements ResultSetTransformer<Map<K, V>> {
    public Map<K, V> transform(ResultSet resultSet) {
        return null;
    }
}