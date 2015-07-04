package cn.limw.summer.mongo.driver;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import cn.limw.summer.mongo.driver.base.AbstractConnection;
import cn.limw.summer.mongo.driver.meta.MongoDatabaseMetaData;
import cn.limw.summer.mongo.driver.util.Logs;
import cn.limw.summer.mongo.driver.util.Urls;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Errors;

import com.mongodb.DB;
import com.mongodb.MongoClient;

/**
 * MongoConnection
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月4日 下午2:11:51)
 */
@SuppressWarnings("resource")
public class MongoConnection extends AbstractConnection {
    private static final Logger log = Logs.get();

    /**
     * 一个MongoDb的引用
     */
    private DB db;

    /**
     * 此时会连接到Mongo
     */
    public MongoConnection(String url) {
        try {
            this.db = new MongoClient(Urls.getHost(url), Urls.getPort(url)).getDB(Urls.getDbName(url));
        } catch (Exception e) {
            throw Errors.wrap(e);
        }
    }

    public DB getDb() {
        return Asserts.noNull(db);
    }

    /**
     * 预处理语句
     */
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        log.trace("prepareStatement() sql = " + sql);
        return new MongoPreparedStatement(this).setSql(sql);
    }

    /**
     * 创建Statement,其实还是PreparedStatement
     */
    public Statement createStatement() throws SQLException {
        log.trace("createStatement()");
        return new MongoPreparedStatement(this);
    }

    /**
     * 返回数据库Meta
     */
    public DatabaseMetaData getMetaData() throws SQLException {
        return MongoDatabaseMetaData.INSTANCE;
    }

    /**
     * 返回数据库名
     */
    public String getCatalog() throws SQLException {
        return getDb().getName();
    }

    /**
     * 链接已关闭
     */
    public boolean isClosed() throws SQLException {
        return null == db;
    }

    /**
     * 关闭链接
     */
    public void close() throws SQLException {
        db = null;
    }
}