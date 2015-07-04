package li.mongo.driver.meta;

import java.sql.SQLException;
import java.util.Map;

import li.mongo.driver.meta.base.AbstractResultSetMetaData;
import li.mongo.driver.util.Logs;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.unblocked.support.collection.DuplexMap;

/**
 * MongoResultSetMetaData
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月5日 上午11:28:09)
 */
public class MongoResultSetMetaData extends AbstractResultSetMetaData {
    private static final Logger log = Logs.get();

    /**
     * 存储所有的列名
     */
    private String[] keys;

    /**
     * 存储列名到列别名的对应,也用于读取列名,第一优先
     */
    private DuplexMap<String, String> alias;

    /**
     * 用于读取列名,第二优先
     */
    private DBObject dbObject;

    /**
     * 用于读取列名,第三优先
     */
    private DBCursor dbCursor;

    /**
     * 如果ResultSet中已经读取过,则dbObject不为空,否则copy一份dbCursor后读一次
     */
    public DBObject getDbObject() {
        return null != dbObject ? dbObject : (dbCursor.hasNext() ? dbCursor.copy().next() : new BasicDBObject());// dbCursor.getKeysWanted()
    }

    /**
     * 首先通过别名提取,别名为空时候为 SELECT * 通过 dbObject 获取
     */
    private String[] getKeys() {
        return null != keys ? keys : (keys = (alias.isEmpty() ? getDbObject().keySet().toArray(new String[0]) : alias.keySet().toArray(new String[0])));
    }

    /**
     * 传入列名到别名的映射,可能为空
     */
    public void setAlias(Map<String, String> _alias) {
        if (null != _alias) {
            this.alias = new DuplexMap<String, String>(_alias);
            log.trace("setAlias() alias=" + _alias);
        }
    }

    /**
     * 返回列总数
     */
    public int getColumnCount() throws SQLException {
        return alias.isEmpty() ? getKeys().length : alias.size(); // alias.isEmpty() 为 SELECT * 场景
    }

    /**
     * 返回列别名,如果没有,则返回列名
     */
    public String getColumnLabel(int column) throws SQLException {
        String columnName = getColumnName(column);
        String columnLabel = (null == alias ? columnName : alias.get(columnName));// 没有alias则返回列名
        return (null == columnLabel ? columnName : columnLabel);// 对应columnName的columnLabel为空则返回columnName
    }

    /**
     * 返回列名
     */
    public String getColumnName(int column) throws SQLException {
        return getKeys().length == 0 ? null : getKeys()[column - 1];// SQL中从1而不是0开始
    }

    /**
     * 根据列别名查询列名,如果没有别名则返回本身
     */
    public String getColumnNameByLabel(String columnLabel) {
        String columnName = null == alias ? columnLabel : alias.getKeyByValue(columnLabel);
        return null == columnName ? columnLabel : columnName;
    }

    public void setDbCursor(DBCursor dbCursor) {
        this.dbCursor = dbCursor;
    }

    public void setDbObject(DBObject dbObject) {
        this.dbObject = dbObject;
    }
}