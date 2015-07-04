package li.mongo.driver.query;

import java.util.Map;

import li.mongo.driver.MongoPreparedStatement;
import li.mongo.driver.model.Term;
import li.mongo.driver.util.Mongos;
import li.mongo.driver.util.Parsers;
import net.sf.jsqlparser.statement.Statement;

import com.mongodb.DBCollection;
import com.unblocked.support.util.Asserts;

/**
 * Query
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月4日 下午2:59:44)
 */
public abstract class Query {
    private MongoPreparedStatement preparedStatement;

    private Statement statement;

    private DBCollection tableCollection;

    private Map<String, Object> where;

    /**
     * 返回筛选条件,带缓存
     */
    public Term getWhereTerm() {
        return new Term(getWhere()).setPreparedStatement(getPreparedStatement());
    }

    public Map<String, Object> getWhere() {
        return null == where ? where = Mongos.getWhere(Parsers.getWhereExpression(getStatement()), getPreparedStatement()) : where;
    }

    public Query setWhere(Map<String, Object> where) {
        this.where = where;
        return this;
    }

    /**
     * 返回对应于表的DBCollection,带缓存
     */
    public DBCollection getTableCollection() {
        return null == tableCollection ? tableCollection = getPreparedStatement().getConnection().getDb().getCollection(Parsers.getTableName(getStatement())) : tableCollection;
    }

    public Query setPreparedStatement(MongoPreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
        return this;
    }

    public MongoPreparedStatement getPreparedStatement() {
        return Asserts.noNull(preparedStatement);
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public Statement getStatement() {
        return this.statement;
    }

    public abstract Query execute();
}