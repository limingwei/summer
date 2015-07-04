package li.mongo.test.nutz;

import li.mongo.BaseTest;

import org.junit.Test;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author li
 * @version 1 2014年3月21日上午10:54:22
 */
public class NutzTest extends BaseTest {
    @Autowired
    NutDao nutDao;

    @Test
    public void list() {
        System.err.println(nutDao.query("t_users", Cnd.cri(), new Pager()));
    }

    @Test
    public void sqls() {
        Sql sql = Sqls.create(" SELECT * FROM t_users LIMIT 3 ");
        nutDao.execute(sql);
        System.err.println(sql.getList(Record.class));
    }
}