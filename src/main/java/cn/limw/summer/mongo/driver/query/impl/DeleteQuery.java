package cn.limw.summer.mongo.driver.query.impl;

import org.apache.log4j.Logger;

import cn.limw.summer.mongo.driver.query.WriteQuery;
import cn.limw.summer.mongo.driver.util.Logs;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * DeleteQuery
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月4日 下午3:06:08)
 */
public class DeleteQuery extends WriteQuery {
    private static final Logger log = Logs.get();

    public WriteQuery execute() {
        DBCollection tableCollection = getTableCollection();
        DBObject where = getWhereTerm();

        log.trace("execute() table=" + tableCollection + ", where=" + where);
        return super.setWriteResult(tableCollection.remove(where));
    }

    public void setSql(String sql) {
        // TODO Auto-generated method stub
        
    }
}