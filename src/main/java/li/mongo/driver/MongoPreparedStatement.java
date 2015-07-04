package li.mongo.driver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import li.mongo.driver.base.AbstractPreparedStatement;
import li.mongo.driver.query.Query;
import li.mongo.driver.query.ReadQuery;
import li.mongo.driver.query.WriteQuery;
import li.mongo.driver.util.Querys;

import com.unblocked.support.util.Errors;

/**
 * MongoPreparedStatement
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月4日 下午2:15:00)
 */
@SuppressWarnings("resource")
public class MongoPreparedStatement extends AbstractPreparedStatement {
    /**
     * 实参列表,key是index
     */
    private Map<Integer, Object> parameters = new HashMap<Integer, Object>();

    /**
     * @see #setNull(int, int)
     */
    private Map<Integer, Object> nullParameters = new HashMap<Integer, Object>();

    /**
     * 顺序读取参数指针
     */
    private Integer parameter_index = 1;

    /**
     * 数据库链接
     */
    private MongoConnection connection;

    /**
     * 查询对象
     */
    private Query query;

    /**
     * SQL语句
     */
    private String sql;

    /**
     * 结果集
     */
    private ResultSet resultSet;

    /**
     * 更新条数
     */
    private Integer updateCount;

    /**
     * 预处理SQL语句,解析SQL结构
     */
    public MongoPreparedStatement(MongoConnection connection) {
        this.connection = connection;
    }

    public MongoPreparedStatement setSql(String sql) {
        this.sql = sql;
        this.query = Querys.parseQuery(sql).setPreparedStatement(this);
        return this;
    }

    public MongoConnection getConnection() {
        return this.connection;
    }

    public String getSql() {
        return this.sql;
    }

    /**
     * 设置参数
     */
    public void setObject(int index, Object value) throws SQLException {
        parameters.put(index, value);
    }

    /**
     * Mongo中有$unset
     */
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        nullParameters.put(parameterIndex, sqlType);
    }

    /**
     * 第N个参数是否通过setNull设置
     * 
     * @param parameterIndex
     */
    public Boolean isNull(Integer parameterIndex) {
        return nullParameters.containsKey(parameterIndex);
    }

    /**
     * 清除参数值
     */
    public void clearParameters() throws SQLException {
        parameters.clear();
        nullParameters.clear();
        parameter_index = 1;
    }

    /**
     * 这是一个有损方法, 返回当前参数索引,并自增之
     */
    public Integer getNextParameterIndex() {
        return parameter_index++;
    }

    /**
     * 返回第index个参数的值(可能是Holder)
     */
    public Object getParameter(Integer index) {
        return parameters.containsKey(index) ? parameters.get(index) : Errors.cast("还没有为第 " + index + " 个参数设值");
    }

    /**
     * 执行语句,如是查询语句返回true
     */
    public boolean execute() throws SQLException {
        if (query instanceof ReadQuery) {
            this.resultSet = ((ReadQuery) query.execute()).getResultSet();
            return true;
        } else {
            this.updateCount = ((WriteQuery) query.execute()).getUpdateCount();
            return false;
        }
    }

    /**
     * 返回结果集
     */
    public ResultSet getResultSet() throws SQLException {
        return this.resultSet;
    }

    /**
     * 返回更新条数
     */
    public int getUpdateCount() throws SQLException {
        return this.updateCount;
    }

    /**
     * 执行查询语句并返回结果集
     */
    public ResultSet executeQuery() throws SQLException {
        return (query instanceof ReadQuery && execute()) ? getResultSet() : (ResultSet) Errors.cast("传入的语句不是查询语句");
    }

    /**
     * 执行更新语句并返回更新条数
     */
    public int executeUpdate() throws SQLException {
        return (query instanceof WriteQuery && !execute()) ? getUpdateCount() : (Integer) Errors.cast("传入的语句不是更新语句");
    }

    /**
     * 定义在接口Statement中的方法,此处按照PreparedStatement执行
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        return new MongoPreparedStatement(getConnection()).setSql(sql).executeQuery();
    }

    public int executeUpdate(String sql) throws SQLException {
        return new MongoPreparedStatement(getConnection()).setSql(sql).executeUpdate();
    }
}