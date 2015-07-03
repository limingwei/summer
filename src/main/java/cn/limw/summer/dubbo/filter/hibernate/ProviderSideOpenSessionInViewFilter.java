package cn.limw.summer.dubbo.filter.hibernate;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * @author li
 * @version 1 (2014年11月19日 下午3:51:28)
 * @since Java7
 * @see org.springframework.orm.hibernate4.support.OpenSessionInViewFilter
 */

//@Activate(group = Constants.PROVIDER)
public class ProviderSideOpenSessionInViewFilter implements Filter {
    private static final Logger log = Logs.slf4j();

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        log.info("invoke before {} {}", invoker, invocation);
        Result result = invoker.invoke(invocation);
        log.info("invoke after {} {}", invoker, invocation);
        return result;
    }
}