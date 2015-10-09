package summer.dao;

import java.sql.ResultSet;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:24:26)
 * @since Java7
 */
public interface ResultSetTransformer<T> {
    public T transform(ResultSet resultSet);
}