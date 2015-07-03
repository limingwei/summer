package cn.limw.summer.dao.summer;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @author li
 * @version 1 (2015年6月23日 下午3:20:09)
 * @since Java7
 */
public class DefaultQueryRunner implements QueryRunner {
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ResultSet executeQuery(String sql) {
        try {
            return getDataSource().getConnection().prepareStatement(sql).executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}