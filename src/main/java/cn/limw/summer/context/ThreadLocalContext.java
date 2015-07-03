package cn.limw.summer.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author li
 * @version 1 (2015年5月25日 上午9:48:45)
 * @since Java7
 * @see com.alibaba.dubbo.rpc.RpcContext
 */
public class ThreadLocalContext {
    private static final ThreadLocal<ThreadLocalContext> THREAD_LOCAL = new ThreadLocal<ThreadLocalContext>() {
        protected ThreadLocalContext initialValue() {
            return new ThreadLocalContext();
        }
    };

    private final Map<String, Object> values = new HashMap<String, Object>();

    public static ThreadLocalContext getContext() {
        return THREAD_LOCAL.get();
    }

    public ThreadLocalContext set(String key, Object value) {
        if (value == null) {
            values.remove(key);
        } else {
            values.put(key, value);
        }
        return this;
    }

    public Object get(String key) {
        return values.get(key);
    }
}