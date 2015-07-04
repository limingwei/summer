package li.mongo.driver.query.impl;

import java.util.Map;

import li.mongo.driver.model.Term;
import li.mongo.driver.query.WriteQuery;
import li.mongo.driver.util.Logs;
import li.mongo.driver.util.Mongos;
import net.sf.jsqlparser.statement.update.Update;

import org.apache.log4j.Logger;

import com.mongodb.DBCollection;
import com.mongodb.WriteResult;

/**
 * UpdateQuery
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月4日 下午3:06:32)
 */
public class UpdateQuery extends WriteQuery {
    private static final Logger log = Logs.get();

    private Map<String, Object> updateFields;

    public Term getUpdateFields() {
        return new Term(null == updateFields ? updateFields = Mongos.getUpdateFields((Update) getStatement(), getPreparedStatement()) : updateFields).setPreparedStatement(getPreparedStatement());
    }

    public WriteQuery execute() {
        DBCollection tableCollection = getTableCollection();
        Term updateFields = getUpdateFields();// 这里顺序不能变动
        Term where = getWhereTerm();

        WriteResult writeResult = tableCollection.update(where, updateFields, false, true);
        log.trace("execute() table=" + tableCollection + ", set=" + updateFields + ", where=" + where);
        return super.setWriteResult(writeResult);
    }
}