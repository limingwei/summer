package cn.limw.summer.dubbo.rpc.util;

import java.util.ArrayList;
import java.util.List;

import cn.limw.summer.util.StringUtil;

import com.alibaba.dubbo.rpc.Invoker;

/**
 * @author li
 * @version 1 (2014年12月23日 下午1:33:48)
 * @since Java7
 */
public class InvokerUtil {
    public static <T> String getFullUrls(List<Invoker<T>> invokers) {
        int size = invokers.size();
        List<String> fullUrls = new ArrayList<String>(size);
        for (Invoker<T> invoker : invokers) {
            fullUrls.add(invoker.getUrl().toFullString());
        }
        return StringUtil.concat(fullUrls.toArray(new String[size]));
    }
}