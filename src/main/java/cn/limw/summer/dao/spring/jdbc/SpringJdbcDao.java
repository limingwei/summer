package cn.limw.summer.dao.spring.jdbc;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import cn.limw.summer.dao.Page;

/**
 * @author li
 * @version 1 (2014年11月14日 上午10:13:29)
 * @since Java7
 */
public class SpringJdbcDao extends SpringJdbcDaoSupport {
    public <T> List<T> list(Class<T> type, Page page) {
        return getJdbcTemplate().query("SELECT * FROM " + getTableName(type) + " LIMIT " + page.getFrom() + "," + page.getPageSize(), new BeanPropertyRowMapper(type));
    }
}