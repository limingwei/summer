package cn.limw.summer.dubbo.filter.entitywriteback;

import cn.limw.summer.dao.Page;
import cn.limw.summer.entity.CreatedAt;
import cn.limw.summer.entity.Id;
import cn.limw.summer.entity.UpdatedAt;

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
 * @version 1 (2015年1月28日 下午4:53:21)
 * @since Java7
 * @see cn.limw.summer.dubbo.filter.entitywriteback.ConsumerSideEntityWriteBackFilter
 */
@Activate(group = Constants.PROVIDER)
public class ProviderSideEntityWriteBackFilter implements Filter {
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = invoker.invoke(invocation);
        RpcContext context = RpcContext.getContext();
        Object[] arguments = invocation.getArguments();

        for (int i = 0; i < arguments.length; i++) {
            Object arg = arguments[i];

            if (arg instanceof Id) {
                context.set("args[" + i + "].id", ((Id) arg).getId());
            }
            if (arg instanceof CreatedAt) {
                context.set("args[" + i + "].createdAt", ((CreatedAt) arg).getCreatedAt());
            }
            if (arg instanceof UpdatedAt) {
                context.set("args[" + i + "].updatedAt", ((UpdatedAt) arg).getUpdatedAt());
            }
            if (arg instanceof Page) {
                context.set("args[" + i + "].recordCount", ((Page) arg).getRecordCount());
            }
        }

        return result;
    }
}