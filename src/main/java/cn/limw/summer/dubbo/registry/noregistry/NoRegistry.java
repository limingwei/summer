package cn.limw.summer.dubbo.registry.noregistry;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.NotifyListener;
import com.alibaba.dubbo.registry.Registry;

/**
 * @author li
 * @version 1 (2015年6月8日 下午4:44:51)
 * @since Java7
 */
public class NoRegistry implements Registry {
    public boolean isAvailable() {
        return true;
    }

    public List<URL> lookup(URL url) {
        return new ArrayList<URL>();
    }

    public URL getUrl() {
        return null;
    }

    public void destroy() {}

    public void register(URL url) {}

    public void unregister(URL url) {}

    public void subscribe(URL url, NotifyListener listener) {}

    public void unsubscribe(URL url, NotifyListener listener) {}
}