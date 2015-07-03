package cn.limw.summer.druid.filter.wall.mysql.provider;

import cn.limw.summer.druid.filter.wall.mysql.visitor.MySqlWallVisitor;

import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallVisitor;

/**
 * @author li
 * @version 1 (2015年1月16日 下午3:59:39)
 * @since Java7
 */
public class MySqlWallProvider extends AbstractMySqlWallProvider {
    public MySqlWallProvider(WallConfig config) {
        super(config);
    }

    public WallVisitor createWallVisitor() {
        return new MySqlWallVisitor(this);
    }
}