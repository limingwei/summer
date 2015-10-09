package summer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import summer.dao.PreparedStatementParameterSetter;
import summer.dao.ResultSetTransformer;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:14:35)
 * @since Java7
 */
public abstract class AbstractSummerDao {
    private DataSource dataSource;

    private PreparedStatementParameterSetter preparedStatementParameterSetter;

    public PreparedStatementParameterSetter getPreparedStatementParameterSetter() {
        return preparedStatementParameterSetter;
    }

    public void setPreparedStatementParameterSetter(PreparedStatementParameterSetter preparedStatementParameterSetter) {
        this.preparedStatementParameterSetter = preparedStatementParameterSetter;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Integer update(String sql, Object[] parameters) {
        try {
            Connection connection = getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            getPreparedStatementParameterSetter().setParameters(preparedStatement, parameters);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T select(String sql, Object[] parameters, ResultSetTransformer<T> resultSetTransformer) {
        try {
            Connection connection = getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            getPreparedStatementParameterSetter().setParameters(preparedStatement, parameters);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetTransformer.transform(resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}