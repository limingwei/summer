package cn.limw.summer.dao.spring.jdbc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.SessionFactory;

import cn.limw.summer.hibernate.HibernateUtil;
import cn.limw.summer.spring.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author li
 * @version 1 (2014年11月14日 上午10:18:00)
 * @since Java7
 */
public class SpringJdbcDaoSupport {
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final Map<Class<?>, String> TABLE_NAMES = new ConcurrentHashMap<Class<?>, String>();

    public String getTableName(Class<?> type) {
        String tableName = TABLE_NAMES.get(type);
        if (null == tableName) {
            TABLE_NAMES.put(type, tableName = HibernateUtil.getTableName(getHibernateSessionFactory(), type));
        }
        return tableName;
    }

    private SessionFactory getHibernateSessionFactory() {
        return getJdbcTemplate().getHibernateSessionFactory();
    }

    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}