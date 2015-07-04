package li.mongo.driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import li.mongo.driver.base.AbstractDriver;
import li.mongo.driver.util.Logs;

import org.apache.log4j.Logger;

/**
 * MongoDriver
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月4日 下午2:01:46)
 */
public class MongoDriver extends AbstractDriver {
    private static final Logger log = Logs.get();

    /**
     * 链接Mongo数据库,并返回Connection
     */
    public Connection connect(String url, Properties info) throws SQLException {
        log.trace("connect() url=" + url);
        return new MongoConnection(Asserts.noNull(url, "Url不能为空"));
    }
}