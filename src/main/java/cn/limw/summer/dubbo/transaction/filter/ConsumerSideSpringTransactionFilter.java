package cn.limw.summer.dubbo.transaction.filter;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * @author li
 * @version 1 (2015年1月27日 下午4:32:00)
 * @since Java7
 */
//@com.alibaba.dubbo.common.extension.Activate(group = com.alibaba.dubbo.common.Constants.CONSUMER)
public class ConsumerSideSpringTransactionFilter implements Filter {
    private static final Logger log = Logs.slf4j();

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        log.info("invoke before {} {}", invoker, invocation);
        Result result = invoker.invoke(invocation);
        log.info("invoke after {} {}", invoker, invocation);
        return result;
    }
}