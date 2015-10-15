package summer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:16:35)
 * @since Java7
 */
public class SummerDao extends AbstractSummerDao {
    public List<Map<String, Object>> listMap(String sql) {
        try {
            Connection connection = getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.err.println(resultSet);
            return null;
        } catch (Exception e) {
            throw (e instanceof RuntimeException) ? (RuntimeException) e : new RuntimeException(e);
        }
    }
}