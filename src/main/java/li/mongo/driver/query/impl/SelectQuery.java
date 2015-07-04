package li.mongo.driver.query.impl;

import java.util.Map;

import li.mongo.driver.MongoResultSet;
import li.mongo.driver.model.Page;
import li.mongo.driver.query.ReadQuery;
import li.mongo.driver.util.Logs;
import li.mongo.driver.util.Mongos;
import li.mongo.driver.util.Parsers;
import net.sf.jsqlparser.statement.select.Select;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * SelectQuery
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月4日 下午2:58:10)
 */
public class SelectQuery extends ReadQuery {
    private static final Logger log = Logs.get();

    private Map<String, String> alias;

    private Map<String, Object> returnFields;

    private DBObject orderBy;

    private Page page;

    public DBObject getOrderBy() {
        return null == orderBy ? orderBy = new BasicDBObject(Mongos.getOrderBy((Select) getStatement())) : orderBy;
    }

    public Page getPage() {
        return (null == page ? page = Mongos.getPage((Select) getStatement(), getPreparedStatement()) : page).setPreparedStatement(getPreparedStatement());
    }

    public Map<String, Object> getReturnFields() {
        return null == returnFields ? returnFields = Mongos.getReturnFields((Select) getStatement()) : returnFields;
    }

    public Map<String, String> getAlias() {
        return null == alias ? alias = Parsers.getAliasOfFields((Select) getStatement(), getReturnFields()) : alias;
    }

    public ReadQuery execute() {
        DBCollection tableCollection = getTableCollection();
        DBObject fields = new BasicDBObject(getReturnFields());// 这里不用Term
        Map<String, String> aliasMap = getAlias();

        DBObject where = getWhereTerm();// 这里顺序不能随意变动
        DBObject orderBy = getOrderBy();
        Page page = getPage();

        log.trace("execute() table=" + tableCollection + ", fields=" + fields + ", where=" + where + ", orderBy=" + orderBy + ", limit=" + page);
        DBCursor resultCursor = tableCollection.find(where, fields);
        resultCursor.sort(orderBy);
        page.setTo(resultCursor);

        MongoResultSet resultSet = new MongoResultSet();
        resultSet.setStatement(getPreparedStatement());
        resultSet.setDBCursor(resultCursor);
        resultSet.setAlias(aliasMap);
        return super.setResultSet(resultSet);
    }
}