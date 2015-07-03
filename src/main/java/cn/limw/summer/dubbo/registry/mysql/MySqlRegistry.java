package cn.limw.summer.dubbo.registry.mysql;

import java.util.List;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.NotifyListener;

/**
 * @author li
 * @version 1 (2015年4月15日 下午2:18:51)
 * @since Java7
 */
public class MySqlRegistry extends AbstractMySqlRegistry {
    public MySqlRegistry(URL url) {
        super(url);
    }

    public boolean isAvailable() {
        return false;
    }

    public void destroy() {}

    public void register(URL url) {}

    public void unregister(URL url) {}

    public void subscribe(URL url, NotifyListener listener) {}

    public void unsubscribe(URL url, NotifyListener listener) {}

    public List<URL> lookup(URL url) {
        return null;
    }
}