package summer.dao.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author li
 * @version 1 (2015年10月16日 下午12:11:05)
 * @since Java7
 */
public class NamedParameterStatement {
    private final PreparedStatement preparedStatement;

    private final Map<String, List<Integer>> indexMap;

    public NamedParameterStatement(Connection connection, String sql, NamedParameterParser namedParameterPreparedStatementParser) throws SQLException {
        NamedParameterParserResult parserResult = namedParameterPreparedStatementParser.parseSql(sql);
        indexMap = parserResult.getIndexMap();
        preparedStatement = connection.prepareStatement(parserResult.getParsedSql());
    }

    private List<Integer> getIndexes(String name) {
        List<Integer> indexes = indexMap.get(name);
        if (indexes == null) {
            throw new IllegalArgumentException("Parameter not found: " + name);
        }
        return indexes;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setObject(String name, Object value) throws SQLException {
        List<Integer> indexes = getIndexes(name);
        for (Integer index : indexes) {
            getPreparedStatement().setObject(index, value);
        }
    }

    public void setString(String name, String value) throws SQLException {
        List<Integer> indexes = getIndexes(name);
        for (Integer index : indexes) {
            getPreparedStatement().setString(index, value);
        }
    }

    public void setInt(String name, int value) throws SQLException {
        List<Integer> indexes = getIndexes(name);
        for (Integer index : indexes) {
            getPreparedStatement().setInt(index, value);
        }
    }

    public void setLong(String name, long value) throws SQLException {
        List<Integer> indexes = getIndexes(name);
        for (Integer index : indexes) {
            getPreparedStatement().setLong(index, value);
        }
    }

    public void setTimestamp(String name, Timestamp value) throws SQLException {
        List<Integer> indexes = getIndexes(name);
        for (Integer index : indexes) {
            getPreparedStatement().setTimestamp(index, value);
        }
    }

    public boolean execute() throws SQLException {
        return getPreparedStatement().execute();
    }

    public ResultSet executeQuery() throws SQLException {
        return getPreparedStatement().executeQuery();
    }

    public int executeUpdate() throws SQLException {
        return getPreparedStatement().executeUpdate();
    }

    public void close() throws SQLException {
        getPreparedStatement().close();
    }

    public void addBatch() throws SQLException {
        getPreparedStatement().addBatch();
    }

    public int[] executeBatch() throws SQLException {
        return getPreparedStatement().executeBatch();
    }
}