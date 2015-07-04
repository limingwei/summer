package cn.limw.summer.mongo.driver.query.impl;

import java.util.Map;

import net.sf.jsqlparser.statement.insert.Insert;

import org.apache.log4j.Logger;

import cn.limw.summer.mongo.driver.model.Term;
import cn.limw.summer.mongo.driver.query.WriteQuery;
import cn.limw.summer.mongo.driver.util.Logs;
import cn.limw.summer.mongo.driver.util.Mongos;

import com.mongodb.DBCollection;

/**
 * InsertQuery
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月4日 下午3:03:05)
 */
public class InsertQuery extends WriteQuery {
    private static final Logger log = Logs.get();

    private Map<String, Object> insertFields;

    public Term getInsertFields() {
        return new Term(null == insertFields ? insertFields = Mongos.getInsertFields((Insert) getStatement(), getPreparedStatement()) : insertFields).setPreparedStatement(getPreparedStatement());
    }

    public WriteQuery execute() {
        DBCollection tableCollection = getTableCollection();
        Term insertFields = getInsertFields();

        log.trace("execute() table=" + tableCollection + ", fields=" + insertFields);
        return super.setWriteResult(tableCollection.insert(insertFields));
    }

    /**
     * getUpdateCount
     */
    public Integer getUpdateCount() {
        return 1;
    }

    public void setSql(String sql) {
        // TODO Auto-generated method stub
        
    }
}