package cn.limw.summer.dubbo.registry.redis;

import com.alibaba.dubbo.common.URL;

/**
 * @author li
 * @version 1 (2014年12月22日 下午2:26:58)
 * @since Java7
 */
public class RedisRegistry extends AbstractRedisRegistry {
    public RedisRegistry(URL url) {
        super(url);
    }
}