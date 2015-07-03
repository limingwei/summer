package cn.limw.summer.mysql.jdbc;

import java.sql.SQLException;

import com.mysql.jdbc.Driver;

/**
 * @author li
 * @version 1 (2015年6月3日 下午2:13:17)
 * @since Java7
 */
public class AbstractDriver extends Driver {
    public AbstractDriver() throws SQLException {
        super();
    }
}