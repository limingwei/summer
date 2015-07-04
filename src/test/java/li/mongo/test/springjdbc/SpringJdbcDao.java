package li.mongo.test.springjdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import li.mongo.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import test.model.User;

/**
 * @author 明伟
 */
public class SpringJdbcDao extends BaseTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    HashMap<String, Object> paramMap = new HashMap<String, Object>();

    @Test
    public void list_1() {
        for (int i = 0; i < 3; i++) {
            String sql = "SELECT username FROM t_users LIMIT 5";
            System.err.println(jdbcTemplate.query(sql, new RowMapper<User>() {
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.set_id(rs.getString("_id"));
                    user.setUsername(rs.getString("username"));
                    return user;
                }
            }));
        }
    }

    @Test
    public void list_2() {
        for (int i = 0; i < 3; i++) {
            String sql = "SELECT _id FROM t_users LIMIT 5";
            System.err.println(jdbcTemplate.queryForList(sql, paramMap, String.class));
        }
    }

    @Test
    public void list_2_2() {
        for (int i = 0; i < 3; i++) {
            String sql_2 = "SELECT username FROM t_users LIMIT 5";
            System.err.println(jdbcTemplate.queryForList(sql_2, paramMap, String.class));
        }
    }

    @Test
    public void list_3_2() {
        for (int i = 0; i < 3; i++) {
            String sql = "SELECT _id, username FROM t_users LIMIT 5";
            System.err.println(jdbcTemplate.queryForList(sql, paramMap));
        }
    }

    @Test
    public void list_3() {
        for (int i = 0; i < 3; i++) {
            String sql = "SELECT * FROM t_users LIMIT 5";
            System.err.println(jdbcTemplate.queryForList(sql, paramMap));
        }
    }

    @Test
    public void list_4() {
        for (int i = 0; i < 3; i++) {
            String sql = "SELECT * FROM t_users LIMIT 5";
            System.err.println(jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class)));
        }
    }

    @Test
    public void list_4_2() {
        for (int i = 0; i < 3; i++) {
            String sql = "SELECT * FROM t_users LIMIT 5";
            System.err.println(jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(User.class)));
        }
    }

    @Test(expected = Exception.class)
    public void callableStatement() {
        List<SqlParameter> declaredParameters = new ArrayList<SqlParameter>();
        jdbcTemplate.getJdbcOperations().call(new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                return con.prepareCall("");
            }
        }, declaredParameters);
    }
}