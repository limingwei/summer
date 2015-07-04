package li.mongo;

import li.mongo.driver.util.QuerysTest;
import li.mongo.driver.util.UrlsTest;
import li.mongo.test.hibernate.HibernateDaoTest;
import li.mongo.test.jdbc.JdbcTest;
import li.mongo.test.mongo.MongoTest;
import li.mongo.test.mybatis.MybatisDaoTest;
import li.mongo.test.nutz.NutzTest;
import li.mongo.test.springjdbc.SpringJdbcDao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UrlsTest.class, QuerysTest.class, JdbcTest.class, SpringJdbcDao.class, HibernateDaoTest.class, MybatisDaoTest.class, MongoTest.class, NutzTest.class })
public class AllTest {}