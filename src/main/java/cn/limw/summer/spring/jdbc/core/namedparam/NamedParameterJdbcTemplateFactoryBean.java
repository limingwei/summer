package cn.limw.summer.spring.jdbc.core.namedparam;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;

import cn.limw.summer.spring.beans.factory.SingletonFactoryBean;

/**
 * @author li
 * @version 1 (2014年11月14日 上午10:46:28)
 * @since Java7
 */
public class NamedParameterJdbcTemplateFactoryBean extends SingletonFactoryBean<NamedParameterJdbcTemplate> {
    private DataSource dataSource;

    private SessionFactory hibernateSessionFactory;

    public NamedParameterJdbcTemplate getObject() throws Exception {
        if (null == getDataSource() && null != getHibernateSessionFactory()) {
            return new NamedParameterJdbcTemplate(getHibernateSessionFactory());
        }
        return new NamedParameterJdbcTemplate(getDataSource());
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SessionFactory getHibernateSessionFactory() {
        return hibernateSessionFactory;
    }

    public void setHibernateSessionFactory(SessionFactory hibernateSessionFactory) {
        this.hibernateSessionFactory = hibernateSessionFactory;
    }
}