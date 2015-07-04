package li.mongo.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 明伟
 */
public class Demo extends BaseTest {
    @Autowired
    DataSource dataSource;

    @Test
    public void main() throws Throwable {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM t_users WHERE id>5 AND id<20 ORDER BY id DESC LIMIT 0,5");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.err.println(resultSet.getObject("_id") + "\t" + resultSet.getInt("id") + "\t" + resultSet.getString("username") + "\t");
        }
    }
}