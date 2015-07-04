package li.mongo.test.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

import org.junit.Test;

import cn.limw.summer.java.sql.util.ResultSetUtil;
import cn.limw.summer.mongo.driver.MongoConnection;

/**
 * @author 明伟
 */
public class JdbcTest {
    private MongoConnection getConnection() {
        return new MongoConnection("jdbc:mongo://127.0.0.1:27017/mongo_db_demo");
    }

    @Test
    public void testIsNotNull() throws Exception {
        String sql = "SELECT COUNT(*) FROM t_users WHERE id IS NOT NULL";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.err.println(ResultSetUtil.toString(preparedStatement.executeQuery()));
    }

    /**
     * @see cn.limw.summer.mongo.driver.util.Mongos#getWhere(net.sf.jsqlparser.expression.Expression, cn.limw.summer.mongo.driver.MongoPreparedStatement)
     */
    @Test
    public void testIsNull() throws Exception {
        String sql = "SELECT COUNT(*) FROM t_users WHERE id IS NULL";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.err.println(ResultSetUtil.toString(preparedStatement.executeQuery()));
    }

    @Test
    public void testCount() throws Exception {
        String sql = "SELECT COUNT(*) FROM t_users";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.err.println(ResultSetUtil.toString(preparedStatement.executeQuery()));
    }

    @Test
    public void testMin() throws Exception {
        String sql = "SELECT MIN(id) min_id FROM t_users WHERE id IS NOT NULL";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.err.println(ResultSetUtil.toString(preparedStatement.executeQuery()));
    }

    @Test
    public void testMax() throws Exception {
        String sql = "SELECT MAX(id) max_id FROM t_users";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.err.println(ResultSetUtil.toString(preparedStatement.executeQuery()));
    }

    @Test
    public void testMax_2() throws Exception {
        String sql = "SELECT id max_id FROM t_users ORDER BY id DESC LIMIT 1";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.err.println(ResultSetUtil.toString(preparedStatement.executeQuery()));
    }

    @Test
    public void testIn() throws Exception {
        String sql = "SELECT * FROM t_users WHERE id NOT IN (?) LIMIT 5";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, -1);
        System.err.println(ResultSetUtil.toString(preparedStatement.executeQuery()));
    }

    @Test
    public void testIn_4() throws Exception {
        String sql = " SELECT * FROM t_users WHERE id IN (?, ?) LIMIT 5";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, 2);
        System.err.println(ResultSetUtil.toString(preparedStatement.executeQuery()));
    }

    @Test
    public void testIn_5() throws Exception {
        String sql = " SELECT * FROM t_users WHERE id IN (?) LIMIT 5";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 1);
        System.err.println(ResultSetUtil.toString(preparedStatement.executeQuery()));
    }

    @Test
    public void testIn_3() throws Exception {
        String sql = "DELETE FROM t_users WHERE id IN (?, 2, ?)";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, 3);
        System.err.println(preparedStatement.executeUpdate());
    }

    @Test
    public void testIn_2() throws Exception {
        String sql = "DELETE FROM t_users WHERE id IN (-1, -2, -3)";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.err.println(preparedStatement.executeUpdate());
    }

    @Test
    public void testUpdate_4() throws Exception {
        String sql = " UPDATE t_users SET id=id+? WHERE id=1";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, -1);
        System.err.println(preparedStatement.executeUpdate());
    }

    @Test
    public void testUpdate_3() throws Exception {
        String sql = " UPDATE t_users SET id=id+-1 WHERE id=1";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.err.println(preparedStatement.executeUpdate());
    }

    @Test
    public void testUpdate_2() throws Exception {
        String sql = " UPDATE t_users SET id=id+? WHERE id=1";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 1);
        System.err.println(preparedStatement.executeUpdate());
    }

    @Test
    public void testUpdate_1() throws Exception {
        String sql = " UPDATE t_users SET id=id+1 WHERE id=1";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.err.println(preparedStatement.executeUpdate());
    }

    @Test
    public void testArray() throws Exception {
        String sql = "SELECT COUNT(*) FROM t_users  WHERE id NOT IN (-1,-2)";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.err.println(ResultSetUtil.toString(resultSet));
    }

    @Test
    public void testUnSet_2() throws Exception {
        String sql = "UPDATE t_users SET username=?, id=? WHERE _id=?";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setNull(1, Types.VARCHAR);
        preparedStatement.setInt(2, 2);
        preparedStatement.setInt(3, 3);
        preparedStatement.executeUpdate();
    }

    @Test
    public void testUnSet_1() throws Exception {
        String sql = "UPDATE t_users SET username=null, id=2 WHERE _id=1";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

    @Test
    public void testStatementUpdate() throws Exception {
        String sql = "DELETE FROM t_users WHERE id IN (-0.5,-2)";
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        System.err.println(statement.executeUpdate(sql));
    }

    @Test
    public void testStatementQuery() throws Exception {
        String sql = "SELECT COUNT(*) FROM t_users  WHERE id NOT IN (1,2,3)";
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        System.err.println(ResultSetUtil.toString(resultSet));
    }

    @Test
    public void notIn() throws Exception {
        String sql = "SELECT COUNT(*) FROM t_users  WHERE id NOT IN (1,2,3)";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.err.println(ResultSetUtil.toString(resultSet));
    }

    @Test
    public void selectWithLike4() throws Exception {
        String sql = "SELECT * FROM t_users  WHERE username LIKE ? LIMIT 5";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%1-1%");
        ResultSet resultSet = preparedStatement.executeQuery();
        System.err.println(ResultSetUtil.toString(resultSet));
    }

    @Test
    public void selectWithLike3() throws Exception {
        String sql = "SELECT * FROM t_users  WHERE username LIKE ? ORDER BY id DESC LIMIT 5";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "^.*1-1.*$");
        ResultSet resultSet = preparedStatement.executeQuery();
        System.err.println(ResultSetUtil.toString(resultSet));
    }

    @Test
    public void selectWithNotLike3_2() throws Exception {
        String sql = "SELECT * FROM t_users  WHERE username NOT LIKE ? ORDER BY id DESC LIMIT 5";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "^.*1-1.*$");
        ResultSet resultSet = preparedStatement.executeQuery();
        System.err.println(ResultSetUtil.toString(resultSet));
    }

    @Test
    public void selectWithLike1() throws Exception {
        String sql = "SELECT * FROM t_users  WHERE username LIKE '^.*1-1.*$' LIMIT 5";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.err.println(ResultSetUtil.toString(resultSet));
    }

    @Test
    public void selectWithLike2() throws Exception {
        String sql = "SELECT * FROM t_users  WHERE username LIKE ? LIMIT 5";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "^.*1-1.*$");
        ResultSet resultSet = preparedStatement.executeQuery();
        System.err.println(ResultSetUtil.toString(resultSet));
    }

    @Test
    public void selectWithAlias() throws Exception {
        for (int i = 0; i < 3; i++) {
            String sql = "select _id,user0_.id as id0_, user0_.username as username0_ from t_users user0_ where user0_.id<? or user0_.id<? or user0_.id<? order by user0_.id desc limit 1,1";
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 11);
            preparedStatement.setInt(2, 22);
            preparedStatement.setInt(3, 33);
            preparedStatement.setInt(4, 1);
            preparedStatement.setInt(5, 3);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.err.println(ResultSetUtil.toString(resultSet));
        }
    }

    @Test
    public void listEmptyTable() throws Exception {
        for (int i = 0; i < 3; i++) {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM aaaaaa WHERE id<5 LIMIT 3");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.err.println(ResultSetUtil.toString(resultSet));
        }
    }

    @Test
    public void countEmptyTable() throws Exception {
        for (int i = 0; i < 3; i++) {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM aaaaaaaa WHERE id<? OR id<?");
            preparedStatement.setInt(1, 11);
            preparedStatement.setInt(2, 22);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.err.println(ResultSetUtil.toString(resultSet));
        }
    }

    @Test
    public void count() throws Exception {
        for (int i = 0; i < 3; i++) {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM t_users WHERE id<? OR id<?");
            preparedStatement.setInt(1, 11);
            preparedStatement.setInt(2, 22);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.err.println(ResultSetUtil.toString(resultSet));
        }
    }

    @Test
    public void delete() throws Exception {
        for (int i = 0; i < 3; i++) {
            Connection connection = getConnection();
            String sql = "DELETE FROM t_users WHERE id<?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            System.err.println(preparedStatement.executeUpdate());
        }
    }

    @Test
    public void count_2() throws Exception {
        for (int i = 0; i < 3; i++) {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM t_users WHERE id<?");
            preparedStatement.setInt(1, 4);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.err.println(ResultSetUtil.toString(resultSet));
        }
    }

    @Test
    public void select_3() throws Exception {
        for (int i = 0; i < 3; i++) {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM t_users WHERE id<? LIMIT 3");
            preparedStatement.setInt(1, 5);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.err.println(resultSet.getObject(1));
            }
        }
    }

    @Test
    public void select_2() throws Exception {
        for (int i = 0; i < 3; i++) {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM t_users WHERE id<5 LIMIT 3");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.err.println(ResultSetUtil.toString(resultSet));
        }
    }

    @Test
    public void select() throws Exception {
        for (int i = 0; i < 3; i++) {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM t_users WHERE id<? OR (id<? OR id<?) ORDER BY id DESC  LIMIT ?,?");// LIMIT 3,2
            preparedStatement.setInt(1, 11);
            preparedStatement.setInt(2, 22);
            preparedStatement.setInt(3, 99);
            preparedStatement.setInt(4, 2);
            preparedStatement.setInt(5, 5);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.err.println(ResultSetUtil.toString(resultSet));
        }
    }

    @Test
    public void insert() throws Exception {
        String sql = "INSERT INTO t_users(id, username) VALUES (?, ?)";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < 5; i++) {
            preparedStatement.setString(1, Math.random() + "-" + i);
            preparedStatement.setString(2, "username-" + System.currentTimeMillis() + "-" + i);
            System.err.println(preparedStatement.executeUpdate());
        }
    }

    @Test
    public void update() throws Exception {
        String sql = "UPDATE t_users SET username=? WHERE id=?";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < 5; i++) {
            preparedStatement.setString(1, "username-" + System.currentTimeMillis() + "-" + i);
            preparedStatement.setString(2, Math.random() + "-" + i);
            System.err.println(preparedStatement.executeUpdate());
        }
    }

    @Test
    public void prepared_statement_cache() throws Exception {
        for (int i = 0; i < 3; i++) {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM t_users WHERE id<? LIMIT 3");
            preparedStatement.setInt(1, 5);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.err.println(ResultSetUtil.toString(resultSet));

            Connection connection2 = getConnection();
            PreparedStatement preparedStatement2 = connection2.prepareStatement("SELECT COUNT(*) FROM t_users WHERE id<? LIMIT 3");
            preparedStatement2.setInt(1, 10);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            System.err.println(ResultSetUtil.toString(resultSet2));
        }
    }

    @Test
    public void preparedStatement() throws Exception {
        String sql = "SELECT * FROM t_users LIMIT 3";
        PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery(sql);// Statement方式
        System.err.println(ResultSetUtil.toString(resultSet));
    }

    @Test
    public void createStatement() throws Exception {
        Statement statement = getConnection().createStatement();
        String sql = "SELECT * FROM t_users LIMIT 3";
        ResultSet resultSet = statement.executeQuery(sql);
        System.err.println(ResultSetUtil.toString(resultSet));
    }
}