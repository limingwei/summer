package cn.limw.summer.mongo.driver.query.impl;

import net.sf.jsqlparser.statement.select.Select;

import org.apache.log4j.Logger;

import cn.limw.summer.mongo.driver.ValueResultSet;
import cn.limw.summer.mongo.driver.query.ReadQuery;
import cn.limw.summer.mongo.driver.util.Logs;
import cn.limw.summer.mongo.driver.util.Parsers;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * @author li
 * @version 1 2014年3月6日下午5:51:41
 */
public class CountQuery extends ReadQuery {
    private static final Logger log = Logs.get();

    private String alias;

    public String getAlias() {
        return null == alias ? alias = Parsers.getAliasOrExpression((Select) getStatement(), 0) : alias;
    }

    public ReadQuery execute() {
        DBCollection tableCollection = getTableCollection();
        DBObject where = getWhereTerm();

        log.trace("execute() table=" + tableCollection + ", where=" + where);
        long count = tableCollection.count(where);
        ValueResultSet resultSet = new ValueResultSet();
        resultSet.setValue(0, getAlias(), count);
        return super.setResultSet(resultSet);
    }
}