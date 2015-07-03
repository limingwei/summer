package cn.limw.summer.dubbo.router.backup;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.cluster.Router;
import com.alibaba.dubbo.rpc.cluster.RouterFactory;

/**
 * @author li
 * @version 1 (2014年12月23日 下午1:11:08)
 * @since Java7
 * @see com.alibaba.dubbo.rpc.cluster.router.script.ScriptRouter
 * @see com.alibaba.dubbo.rpc.cluster.router.script.ScriptRouterFactory
 */
public class BackupRouterFactory implements RouterFactory {
    public Router getRouter(URL url) {
        return new BackupRouter(url);
    }
}