package li.mongo.driver.util;

import org.junit.Assert;
import org.junit.Test;

import cn.limw.summer.mongo.driver.util.Urls;

/**
 * @author li
 * @version 1 2014年3月7日下午2:34:40
 */
public class UrlsTest {
    private String url = "jdbc:mongo://localhost/mongo_db_demo";

    @Test
    public void getHost() {
        Assert.assertEquals("localhost", Urls.getHost(url));
    }

    @Test
    public void getPort() {
        Assert.assertEquals(27017, Urls.getPort(url));
    }

    @Test
    public void getDbName() {
        Assert.assertEquals("mongo_db_demo", Urls.getDbName(url));
    }
}
