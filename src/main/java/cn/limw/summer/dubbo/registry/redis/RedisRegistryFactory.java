package cn.limw.summer.dubbo.registry.redis;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.RegistryFactory;

/**
 * @author li
 * @version 1 (2014年12月22日 下午2:22:20)
 * @since Java7
 */
public class RedisRegistryFactory implements RegistryFactory {
    public Registry getRegistry(URL url) {
        return new RedisRegistry(url);
    }
}