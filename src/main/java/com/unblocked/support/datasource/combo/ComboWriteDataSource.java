package com.unblocked.support.datasource.combo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;

import cn.limw.summer.java.sql.wrapper.DataSourceWrapper;
import cn.limw.summer.spring.util.SpringUtil;

/**
 * ComboWriteDataSource
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月4日 下午4:57:12)
 */
public class ComboWriteDataSource extends DataSourceWrapper {
    @Autowired
    ApplicationContextAware applicationContextAware;

    private Set<DataSource> dataSources = new HashSet<DataSource>();

    public Set<DataSource> getDataSources() {
        return this.dataSources;
    }

    public DataSource getDataSource() {
        return getDataSources().iterator().next();
    }

    public void setDataSourceIds(String dataSourceIds) {
        String[] _dataSourceIds = dataSourceIds.split(",");
        for (String _dataSourceId : _dataSourceIds) {
            dataSources.add((DataSource) SpringUtil.getBean(applicationContextAware, _dataSourceId));
        }
    }

    public Connection getConnection() throws SQLException {
        ComboConnection connection = new ComboConnection();
        for (DataSource dataSource : getDataSources()) {
            connection.add(dataSource.getConnection());
        }
        return connection;
    }
}