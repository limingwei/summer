package li.mongo.test.mybatis;

import java.util.Map;

import li.mongo.BaseTest;

import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import test.model.User;

import com.unblocked.support.util.MapUtil;

/**
 * @author 明伟
 */
public class MybatisDaoTest extends BaseTest {
    @Autowired
    SqlSessionTemplate sessionTemplate;

    @Test
    public void list() {
        for (int i = 0; i < 3; i++) {
            Map<?, ?> parameter = MapUtil.toMap("id1", 11, "id2", 22, "id3", 33);
            System.err.println(sessionTemplate.selectList("user.list", parameter));
        }
    }

    @Test
    public void save() {
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setUsername("123-" + Math.random() + "-" + i);
            System.err.println(sessionTemplate.insert("user.insert", user));
        }
    }

    @Test
    public void delete() {
        for (int i = 0; i < 3; i++) {
            System.err.println(sessionTemplate.delete("user.delete", 123));
        }
    }

    @Test
    public void update() {
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.set_id("111");
            user.setUsername("222");
            System.err.println(sessionTemplate.update("user.update", user));
        }
    }
}
