package cn.limw.summer.dubbo.filter.log;

import org.slf4j.Logger;

import cn.limw.summer.dubbo.common.util.UrlUtil;
import cn.limw.summer.java.collection.NiceToStringList;
import cn.limw.summer.util.Logs;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * @author li
 * @version 1 (2015年5月25日 下午2:35:39)
 * @since Java7
 */
@Activate(group = Constants.CONSUMER)
public class ConsumerSideInvokeLogFilter implements Filter {
    private static final Logger log = Logs.slf4j();

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long start = System.currentTimeMillis();
        Result result = invoker.invoke(invocation);
        long time = System.currentTimeMillis() - start;
        log(invoker, invocation, time);
        return result;
    }

    public static void log(Invoker<?> invoker, Invocation invocation, long time) {
        URL url = invoker.getUrl();
        String urlString = UrlUtil.toSimpleString(url) + ", " + invocation.getMethodName() + "() " + new NiceToStringList(invocation.getParameterTypes());
        if (time > 1000) {
            log.error("invoker.url={}, 耗时: {} ms", urlString, time);
        } else if (time > 600) {
            log.warn("invoker.url={}, 耗时: {} ms", urlString, time);
        } else if (time > 300) {
            log.info("invoker.url={}, 耗时: {} ms", urlString, time);
        } else if (time > 100) {
            log.debug("invoker.url={}, 耗时: {} ms", urlString, time);
        } else {
            log.trace("invoker.url={}, 耗时: {} ms", urlString, time);
        }
    }
}