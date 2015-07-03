package cn.limw.summer.dubbo.loadbalance.backup;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;

import cn.limw.summer.dubbo.rpc.util.InvokerFullUrlComparator;
import cn.limw.summer.dubbo.rpc.util.InvokerUtil;
import cn.limw.summer.util.Logs;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.LoadBalance;

/**
 * @author li
 * @version 1 (2014年12月23日 下午1:11:13)
 * @since Java7
 * @see com.alibaba.dubbo.rpc.cluster.loadbalance.RandomLoadBalance
 * @see com.alibaba.dubbo.rpc.cluster.loadbalance.RoundRobinLoadBalance
 * @see com.alibaba.dubbo.rpc.cluster.loadbalance.LeastActiveLoadBalance
 */
public class BackupLoadBalance implements LoadBalance {
    private static final Logger log = Logs.slf4j();

    public <T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
        Invoker<T> invoker = getFirstInvoker(invokers);
        log.info("invokers=" + InvokerUtil.getFullUrls(invokers) + ", result=" + invoker.getUrl().toFullString());
        return invoker;
    }

    private <T> Invoker<T> getFirstInvoker(List<Invoker<T>> invokers) {
        Invoker<T>[] array = invokers.toArray(new Invoker[invokers.size()]);
        Arrays.sort(array, new InvokerFullUrlComparator<T>());
        return array[0];
    }
}