package cn.limw.summer.java.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import cn.limw.summer.java.sql.wrapper.PreparedStatementWrapper;
import cn.limw.summer.spring.jdbc.core.JustReturnResultSetExtractor;
import cn.limw.summer.spring.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author li
 * @version 1 (2014年11月17日 下午2:41:18)
 * @since Java7
 */
public class NamedParameterPreparedStatement extends PreparedStatementWrapper {
    private Connection connection;

    private String sql;

    /**
     * 具名参数
     */
    private Map<String, Object> parameters = new HashMap<String, Object>();

    /**
     * 索引参数
     */
    private Map<Integer, Object> arguments = new HashMap<Integer, Object>();

    private int fetchSize = 0;

    public NamedParameterPreparedStatement(Connection connection, String sql) {
        this.connection = connection;
        this.sql = sql;
    }

    public void setParameters(HashMap<String, Object> parameters) {
        this.parameters = parameters;
    }

    public ResultSet executeQuery() throws SQLException {
        if (null == parameters || parameters.isEmpty()) {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(new ConnectionDataSource(connection));
            return jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement prepareStatement = connection.prepareStatement(sql);
                    prepareStatement.setFetchSize(fetchSize);
                    return prepareStatement;
                }
            }, new ArgumentPreparedStatementSetter(getArgumentArray()), JustReturnResultSetExtractor.INSTANCE);
        } else {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(new ConnectionDataSource(connection));
            return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters), JustReturnResultSetExtractor.INSTANCE);
        }
    }

    public Object[] getArgumentArray() {
        Object[] argumentArray = new Object[arguments.size()];
        for (int i = 0; i < argumentArray.length; i++) {
            argumentArray[i] = arguments.get(i);
        }
        return argumentArray;
    }

    public int executeUpdate() throws SQLException {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(new ConnectionDataSource(connection));
        return namedParameterJdbcTemplate.update(sql, parameters);
    }

    public void setFetchSize(int fetchSize) throws SQLException {
        this.fetchSize = fetchSize;
    }

    public void setInt(int parameterIndex, int value) throws SQLException {
        arguments.put(parameterIndex, value);
    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        arguments.put(parameterIndex, null);
    }
}