package cn.limw.summer.mongo.driver.query.impl;

import java.util.Map;

import net.sf.jsqlparser.statement.select.Select;

import org.apache.log4j.Logger;

import cn.limw.summer.mongo.driver.MongoResultSet;
import cn.limw.summer.mongo.driver.model.Page;
import cn.limw.summer.mongo.driver.query.ReadQuery;
import cn.limw.summer.mongo.driver.util.Logs;
import cn.limw.summer.mongo.driver.util.Mongos;
import cn.limw.summer.mongo.driver.util.Parsers;

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

    public void setSql(String sql) {
        // TODO Auto-generated method stub
        
    }

    public void setTable(String string) {
        // TODO Auto-generated method stub
        
    }

    public void setSkip(long offset) {
        // TODO Auto-generated method stub
        
    }

    public void setLimit(long rowCount) {
        // TODO Auto-generated method stub
        
    }
}