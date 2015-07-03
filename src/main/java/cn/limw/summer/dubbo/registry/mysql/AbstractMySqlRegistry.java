package cn.limw.summer.dubbo.registry.mysql;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.Registry;

/**
 * @author li
 * @version 1 (2015年4月15日 下午5:12:52)
 * @since Java7
 */
public abstract class AbstractMySqlRegistry implements Registry {
    private URL url;

    public AbstractMySqlRegistry(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return this.url;
    }
}