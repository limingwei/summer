package cn.limw.summer.context;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

import com.alibaba.dubbo.rpc.RpcContext;

/**
 * 封装的上下文工具类 , 用 com.alibaba.dubbo.rpc.RpcContext 或者 java.lang.ThreadLocal
 * @author li
 * @version 1 (2015年5月25日 上午9:33:32)
 * @since Java7
 */
public class Context {
    private static final Logger log = Logs.slf4j();

    /**
     * @param key 尽量使用全局问唯一的键
     * @param value
     */
    public static void set(String key, Object value) {
        try {
            ThreadLocalContext.getContext().set(key, value);
        } catch (Throwable e) {
            log.error("ThreadLocalContext set() error key=" + key + ", value=" + value, e);
        }
        try {
            RpcContext.getContext().set(key, value);
        } catch (Throwable e) {
            log.error("RpcContext set() error key=" + key + ", value=" + value, e);
        }
    }

    public static Object get(String key) {
        Object value = null;
        try {
            value = ThreadLocalContext.getContext().get(key);
        } catch (Throwable e) {
            log.error("RpcContext get() error key=" + key, e);
        }
        if (null == value) {
            try {
                value = RpcContext.getContext().get(key);
            } catch (Throwable e) {
                log.error("RpcContext get() error key=" + key, e);
            }
        }
        return value;
    }
}