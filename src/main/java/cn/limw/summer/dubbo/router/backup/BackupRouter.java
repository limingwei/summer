package cn.limw.summer.dubbo.router.backup;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;

import cn.limw.summer.dubbo.rpc.util.InvokerFullUrlComparator;
import cn.limw.summer.dubbo.rpc.util.InvokerUtil;
import cn.limw.summer.util.ListUtil;
import cn.limw.summer.util.Logs;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.Router;

/**
 * @author li
 * @version 1 (2014年12月23日 下午1:11:13)
 * @since Java7
 * @see com.alibaba.dubbo.rpc.cluster.router.script.ScriptRouter
 * @see com.alibaba.dubbo.rpc.cluster.router.script.ScriptRouterFactory
 */
public class BackupRouter implements Router {
    private static final Logger log = Logs.slf4j();

    private final URL url;

    public URL getUrl() {
        return url;
    }

    public BackupRouter(URL url) {
        this.url = url;
    }

    public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
        if (invokers == null || invokers.size() == 0) {
            return invokers;
        }
        List<Invoker<T>> result = ListUtil.asList(getFirstInvoker(invokers));
        log.info("invokers=" + InvokerUtil.getFullUrls(invokers) + ", result=" + InvokerUtil.getFullUrls(result));
        return result;
    }

    private <T> Invoker<T> getFirstInvoker(List<Invoker<T>> invokers) {
        Collections.sort(invokers, new InvokerFullUrlComparator<T>());
        return invokers.get(0);
    }

    public int compareTo(Router other) {
        if (other == null || other.getClass() != BackupRouter.class) {
            return 1;
        } else {
            return url.toFullString().compareTo(((BackupRouter) other).url.toFullString());
        }
    }
}