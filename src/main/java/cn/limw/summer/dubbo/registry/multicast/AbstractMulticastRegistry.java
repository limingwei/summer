package cn.limw.summer.dubbo.registry.multicast;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.multicast.MulticastRegistry;

/**
 * @author li
 * @version 1 (2015年6月3日 下午1:57:44)
 * @since Java7
 */
public class AbstractMulticastRegistry extends MulticastRegistry {
    public AbstractMulticastRegistry(URL url) {
        super(url);
    }
}