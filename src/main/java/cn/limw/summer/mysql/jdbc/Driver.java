package cn.limw.summer.mysql.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author li
 * @version 1 (2014年9月29日 下午1:52:32)
 * @since Java7
 */
public class Driver extends AbstractDriver {
    public Driver() throws SQLException {
        super();
    }

    public boolean acceptsURL(String url) throws SQLException {
        return super.acceptsURL(url);
    }

    public Connection connect(String url, Properties properties) throws SQLException {
        return new ConnectionImpl(super.connect(url, properties));
    }
}