package cn.limw.summer.druid.filter.wall.mysql.visitor;

import com.alibaba.druid.wall.WallProvider;
import com.alibaba.druid.wall.spi.MySqlWallVisitor;

/**
 * @author li
 * @version 1 (2015年1月16日 下午4:19:49)
 * @since Java7
 */
public class AbstractMySqlWallVisitor extends MySqlWallVisitor {
    public AbstractMySqlWallVisitor(WallProvider provider) {
        super(provider);
    }
}