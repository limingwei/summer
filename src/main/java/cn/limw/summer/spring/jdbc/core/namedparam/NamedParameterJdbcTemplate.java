package cn.limw.summer.spring.jdbc.core.namedparam;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import cn.limw.summer.hibernate.SessionFactoryDataSource;

/**
 * @author li
 * @version 1 (2014年11月14日 上午11:02:14)
 * @since Java7
 */
public class NamedParameterJdbcTemplate extends AbstractNamedParameterJdbcTemplate {
    private SessionFactory hibernateSessionFactory;

    public NamedParameterJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public NamedParameterJdbcTemplate(SessionFactory sessionFactory) {
        super(new SessionFactoryDataSource(sessionFactory));
        this.hibernateSessionFactory = sessionFactory;
    }

    public SessionFactory getHibernateSessionFactory() {
        return hibernateSessionFactory;
    }

    public void setHibernateSessionFactory(SessionFactory hibernateSessionFactory) {
        this.hibernateSessionFactory = hibernateSessionFactory;
    }

    public PreparedStatementCreator getPreparedStatementCreator(String sql, SqlParameterSource paramSource) {
        return super.getPreparedStatementCreator(sql, paramSource);
    }
}