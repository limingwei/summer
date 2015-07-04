package li.mongo.test.hibernate;

import java.util.List;

import li.mongo.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.dao.impl.UserDaoImpl;
import test.model.User;

import com.unblocked.support.dao.Page;

/**
 * @author 明伟
 */
public class HibernateDaoTest extends BaseTest {
    @Autowired
    UserDaoImpl userDao;

    @Test
    public void list() {
        for (int i = 0; i < 3; i++) {
            String hql = "FROM User WHERE _id<? OR (_id<? OR _id<?) ORDER BY _id DESC";
            Object[] args = { "11", "22", "33" };
            List<User> list = userDao.list(new Page(1, 5), hql, args);
            for (User user : list) {
                System.err.println(user);
            }
        }
    }

    @Test
    public void save() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.set_id("id-" + Math.random() + "-" + i);
            user.setUsername("user-name-" + System.currentTimeMillis() + "-" + i);
            userDao.save(user);
        }
    }

    @Test
    public void delete() {
        for (int i = 0; i < 5; i++) {
            userDao.delete("1");
        }
    }

    @Test
    public void update() {
        for (int i = 0; i < 5; i++) {
            userDao.update("UPDATE User SET username=? WHERE id=?", "22222222", "111111111");
        }
    }
}