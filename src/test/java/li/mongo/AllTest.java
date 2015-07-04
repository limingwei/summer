package li.mongo;

import li.mongo.driver.util.QuerysTest;
import li.mongo.driver.util.UrlsTest;
import li.mongo.test.jdbc.JdbcTest;
import li.mongo.test.mongo.MongoTest;
import li.mongo.test.nutz.NutzTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UrlsTest.class, QuerysTest.class, JdbcTest.class,MongoTest.class, NutzTest.class })
public class AllTest {}