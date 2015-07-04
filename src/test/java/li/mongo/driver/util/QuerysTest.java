package li.mongo.driver.util;

import org.junit.Assert;
import org.junit.Test;

public class QuerysTest {
    @Test
    public void isLimitBA() {
        Assert.assertFalse(Querys.isLimitBA("SELECT * FROM t_users"));
        Assert.assertFalse(Querys.isLimitBA("SELECT * FROM t_users LIMIT 1"));
        Assert.assertTrue(Querys.isLimitBA("SELECT * FROM t_users LIMIT 2,3"));
        Assert.assertFalse(Querys.isLimitBA("SELECT * FROM t_users LIMIT 4 OFFSET 5"));
        Assert.assertFalse(Querys.isLimitBA("SELECT * FROM t_users LIMIT ?"));
        Assert.assertTrue(Querys.isLimitBA("SELECT * FROM t_users LIMIT ?,?"));
        Assert.assertFalse(Querys.isLimitBA("SELECT * FROM t_users LIMIT ? OFFSET ?"));
    }
}
