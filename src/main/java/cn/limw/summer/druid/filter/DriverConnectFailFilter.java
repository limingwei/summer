package cn.limw.summer.druid.filter;

import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

import com.alibaba.druid.filter.FilterAdapter;
import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;

/**
 * @author li
 * @version 1 (2014年12月26日 上午11:38:16)
 * @since Java7
 */
public class DriverConnectFailFilter extends FilterAdapter {
    private static final Logger log = Logs.slf4j();

    public ConnectionProxy connection_connect(FilterChain chain, Properties info) throws SQLException {
        try {
            return super.connection_connect(chain, info);
        } catch (Exception e) {
            log.error("DriverConnectFailed Properties=" + info + ", url=" + chain.getDataSource().getUrl() + ", rawJdbcUrl=" + chain.getDataSource().getRawJdbcUrl(), e);
            throw e;
        }
    }
}