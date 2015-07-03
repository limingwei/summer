package cn.limw.summer.dubbo.registry.noregistry;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.RegistryFactory;

/**
 * @author li
 * @version 1 (2015年6月8日 下午4:44:08)
 * @since Java7
 */
public class NoRegistryFactory implements RegistryFactory {
    public Registry getRegistry(URL url) {
        return new NoRegistry();
    }
}