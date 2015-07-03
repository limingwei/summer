package cn.limw.summer.dubbo.filter.anchor;

import cn.limw.summer.slf4j.logger.AnchorLogger;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * @author li
 * @version 1 (2015年6月29日 上午10:54:37)
 * @since Java7
 * @see cn.limw.summer.slf4j.logger.AnchorLogger
 */
@Activate(group = Constants.CONSUMER)
public class ConsumerSideLoggerAnchorFilter implements Filter {
    protected static final String LOGGER_ANCHOR = "LOGGER_ANCHOR";

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext.getContext().setAttachment(LOGGER_ANCHOR, AnchorLogger.getLoggerAnchor());
        Result result = invoker.invoke(invocation);
        return result;
    }
}