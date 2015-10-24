package summer.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import summer.dao.ResultSetTransformer;
import summer.dao.result.EntityListResultSetTransformer;
import summer.dao.result.MapListResultSetTransformer;
import summer.dao.statement.NamedParameterParser;
import summer.dao.statement.NamedParameterStatement;
import summer.dao.util.NamedParameterStatementUtil;
import summer.util.Assert;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:16:35)
 * @since Java7
 */
public class SummerDao extends AbstractSummerDao {
    private NamedParameterParser namedParameterParser = new NamedParameterParser();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> List<T> listEntity(Class<T> type, String sql, Map<String, Object> params) {
        return doList(new EntityListResultSetTransformer(type), sql, params);
    }

    public List<Map<String, Object>> listMap(String sql, Map<String, Object> params) {
        return doList(new MapListResultSetTransformer(), sql, params);
    }

    private <T> T doList(ResultSetTransformer<T> resultSetTransformer, String sql, Map<String, Object> params) {
        try {
            DataSource dataSource = getDataSource();
            Assert.noNull(dataSource, "dataSource is null");

            Connection connection = dataSource.getConnection();

            NamedParameterStatement preparedStatement = new NamedParameterStatement(connection, sql, namedParameterParser);

            NamedParameterStatementUtil.setParameters(preparedStatement, params);

            ResultSet resultSet = preparedStatement.executeQuery();

            T mapList = resultSetTransformer.transform(resultSet);

            resultSet.close();
            preparedStatement.close();
            connection.close();

            return mapList;
        } catch (Exception e) {
            throw (e instanceof RuntimeException) ? (RuntimeException) e : new RuntimeException(e);
        }
    }
}