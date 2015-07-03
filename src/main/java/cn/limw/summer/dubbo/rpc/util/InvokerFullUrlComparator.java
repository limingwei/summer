package cn.limw.summer.dubbo.rpc.util;

import java.util.Comparator;

import com.alibaba.dubbo.rpc.Invoker;

/**
 * @author li
 * @version 1 (2014年12月23日 下午1:35:42)
 * @param <T>
 * @since Java7
 */
public class InvokerFullUrlComparator<T> implements Comparator<Invoker<T>> {
    public int compare(Invoker<T> o1, Invoker<T> o2) {
        return o1.getUrl().toFullString().compareTo(o2.getUrl().toFullString());
    }
}