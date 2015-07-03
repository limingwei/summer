package cn.limw.summer.dubbo.registry.multicast;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.support.AbstractRegistryFactory;

/**
 * @author li
 * @version 1 (2014年12月1日 下午5:54:35)
 * @since Java7
 * @see com.alibaba.dubbo.registry.multicast.MulticastRegistryFactory
 */
public class MulticastRegistryFactory extends AbstractRegistryFactory {
    private static final Logger log = Logs.slf4j();

    public Registry createRegistry(URL url) {
        log.info("createRegistry ", url.toFullString());

        //        com.alibaba.dubbo.rpc.cluster.support.FailoverCluster

        return new MulticastRegistry(url);
    }
}