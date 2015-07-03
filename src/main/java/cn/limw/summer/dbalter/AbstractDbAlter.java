package cn.limw.summer.dbalter;

import javax.sql.DataSource;

import org.slf4j.Logger;

import cn.limw.summer.dao.jdbc.util.JdbcUtil;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年5月18日 下午3:27:57)
 * @since Java7
 */
public abstract class AbstractDbAlter implements DbAlter {
    private static final Logger log = Logs.slf4j();

    public void runSql(DataSource dataSource, String sql) {
        log.info("{} execute sql update {}", this, sql);
        JdbcUtil.executeUpdate(dataSource, sql);
    }
}