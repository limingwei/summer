package li.mongo.driver;

import java.sql.SQLException;
import java.util.Map;

import li.mongo.driver.base.AbstractResultSet;
import li.mongo.driver.meta.MongoResultSetMetaData;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.unblocked.support.util.Asserts;

/**
 * MongoResultSet
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月4日 下午2:18:26)
 */
public class MongoResultSet extends AbstractResultSet {
    private MongoPreparedStatement preparedStatement;

    private MongoResultSetMetaData resultSetMetaData;

    private DBObject dbObject;

    private DBCursor dbCursor;

    private Map<String, String> alias;

    public void setStatement(MongoPreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public MongoPreparedStatement getPreparedStatement() {
        return Asserts.noNull(this.preparedStatement);
    }

    public DBCursor getDbCursor() {
        return Asserts.noNull(this.dbCursor);
    }

    public void setDBCursor(DBCursor dbCursor) {
        this.dbCursor = dbCursor;
    }

    public void setAlias(Map<String, String> alias) {
        this.alias = alias;
    }

    public DBObject getDbObject() {
        return this.dbObject;
    }

    public boolean next() throws SQLException {
        if (getDbCursor().hasNext()) {
            this.dbObject = getDbCursor().next();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 所有取值方法的根方法
     */
    public Object getObject(String columnLabel) throws SQLException {
        return getDbObject().get(getColumnNameByLabel(columnLabel));
    }

    /**
     * 返回ResultSetMetaData
     */
    public MongoResultSetMetaData getMetaData() throws SQLException {
        if (null == this.resultSetMetaData) {
            MongoResultSetMetaData metaData = new MongoResultSetMetaData();
            metaData.setDbObject(getDbObject());
            metaData.setDbCursor(getDbCursor());
            metaData.setAlias(alias);
            resultSetMetaData = metaData;
        }
        return this.resultSetMetaData;
    }

    /**
     * 根据列别名查询列名
     */
    public String getColumnNameByLabel(String columnLabel) throws SQLException {
        return ((MongoResultSetMetaData) getMetaData()).getColumnNameByLabel(columnLabel);
    }
}