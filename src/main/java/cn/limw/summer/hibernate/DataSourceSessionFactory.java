package cn.limw.summer.hibernate;

import javax.sql.DataSource;

import cn.limw.summer.hibernate.wrapper.SessionFactoryWrapper;

/**
 * @author li
 * @version 1 (2015年6月25日 下午5:23:51)
 * @since Java7
 */
public class DataSourceSessionFactory extends SessionFactoryWrapper {
    private static final long serialVersionUID = -8318402728193850260L;

    private DataSource dataSource;

    public DataSourceSessionFactory(DataSource dataSource) {
        setDataSource(dataSource);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}