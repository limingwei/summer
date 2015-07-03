package cn.limw.summer.dubbo.filter.entitywriteback;

import java.sql.Timestamp;

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
 * @see cn.limw.summer.dubbo.filter.entitywriteback.ProviderSideEntityWriteBackFilter
 */
@Activate(group = Constants.CONSUMER)
public class ConsumerSideEntityWriteBackFilter implements Filter {
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = invoker.invoke(invocation);
        RpcContext context = RpcContext.getContext();
        Object[] arguments = invocation.getArguments();

        for (int i = 0; i < arguments.length; i++) {
            Object arg = arguments[i];

            if (arg instanceof Id) {
                Integer id = (Integer) context.get("args[" + i + "].id");
                if (null != id) {
                    ((Id) arg).setId(id);
                }
            }
            if (arg instanceof CreatedAt) {
                Timestamp createdAt = (Timestamp) context.get("args[" + i + "].createdAt");
                if (null != createdAt) {
                    ((CreatedAt) arg).setCreatedAt(createdAt);
                }
            }
            if (arg instanceof UpdatedAt) {
                Timestamp updatedAt = (Timestamp) context.get("args[" + i + "].updatedAt");
                if (null != updatedAt) {
                    ((UpdatedAt) arg).setUpdatedAt(updatedAt);
                }
            }
            if (arg instanceof Page) {
                Integer recordCount = (Integer) context.get("args[" + i + "].recordCount");
                if (null != recordCount) {
                    ((Page) arg).setRecordCount(recordCount);
                }
            }
        }

        return result;
    }
}