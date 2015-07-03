package cn.limw.summer.druid.filter.wall;

import java.sql.SQLException;

import com.alibaba.druid.proxy.jdbc.DataSourceProxy;

/**
 * @author li
 * @version 1 (2015年1月16日 下午3:44:04)
 * @since Java7
 */
public class WallFilter extends AbstractWallFilter {
    public synchronized void init(DataSourceProxy dataSource) {
        super.init(dataSource);

        if (getWallProvider() instanceof com.alibaba.druid.wall.spi.MySqlWallProvider) {
            setWallProvider(new cn.limw.summer.druid.filter.wall.mysql.provider.MySqlWallProvider(getWallProvider().getConfig()));
        }
    }

    public String check(String sql) throws SQLException {
        return super.check(sql);
    }
}