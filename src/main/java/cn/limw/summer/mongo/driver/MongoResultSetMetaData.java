package cn.limw.summer.mongo.driver;

import java.sql.SQLException;

import cn.limw.summer.java.sql.wrapper.ResultSetMetaDataWrapper;

import com.mongodb.DBObject;

/**
 * @author 明伟
 */
public class MongoResultSetMetaData extends ResultSetMetaDataWrapper {
    private String[] keys = new String[0];

    public MongoResultSetMetaData setDbObject(DBObject dbObject) {
        this.keys = dbObject.keySet().toArray(this.keys);
        return this;
    }

    public String getColumnLabel(int column) throws SQLException {
        return this.keys[column];
    }
}