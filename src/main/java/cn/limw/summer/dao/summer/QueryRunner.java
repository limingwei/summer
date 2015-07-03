package cn.limw.summer.dao.summer;

import java.sql.ResultSet;

/**
 * @author li
 * @version 1 (2015年6月23日 下午3:14:12)
 * @since Java7
 */
public interface QueryRunner {
    ResultSet executeQuery(String sql);
}