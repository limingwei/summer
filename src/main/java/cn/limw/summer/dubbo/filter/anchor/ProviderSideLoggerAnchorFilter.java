package cn.limw.summer.dubbo.filter.anchor;

import cn.limw.summer.slf4j.logger.AnchorLogger;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * @author li
 * @version 1 (2015年6月29日 上午10:55:05)
 * @since Java7
 * @see cn.limw.summer.slf4j.logger.AnchorLogger
 */
@Activate(group = Constants.PROVIDER)
public class ProviderSideLoggerAnchorFilter {
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        AnchorLogger.setLoggerAnchor(RpcContext.getContext().getAttachment(ConsumerSideLoggerAnchorFilter.LOGGER_ANCHOR));
        Result result = invoker.invoke(invocation);
        return result;
    }
}