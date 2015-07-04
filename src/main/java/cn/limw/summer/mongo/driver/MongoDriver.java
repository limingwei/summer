package cn.limw.summer.mongo.driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import cn.limw.summer.mongo.driver.base.AbstractDriver;
import cn.limw.summer.mongo.driver.util.Logs;
import cn.limw.summer.util.Asserts;

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