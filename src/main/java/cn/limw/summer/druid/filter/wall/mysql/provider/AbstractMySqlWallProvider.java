package cn.limw.summer.druid.filter.wall.mysql.provider;

import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.spi.MySqlWallProvider;

/**
 * @author li
 * @version 1 (2015年1月16日 下午3:59:12)
 * @since Java7
 */
public abstract class AbstractMySqlWallProvider extends MySqlWallProvider {
    public AbstractMySqlWallProvider(WallConfig config) {
        super(config);
    }
}