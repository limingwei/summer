package cn.limw.summer.dubbo.registry.mysql;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.RegistryFactory;

/**
 * @author li
 * @version 1 (2015年4月15日 下午2:19:10)
 * @since Java7
 */
public class MySqlRegistryFactory implements RegistryFactory {
    public Registry getRegistry(URL url) {
        return new MySqlRegistry(url);
    }
}