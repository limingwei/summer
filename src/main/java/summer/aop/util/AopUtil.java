package summer.aop.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import summer.aop.AopFilter;
import summer.aop.annotation.Aop;
import summer.aop.annotation.Transaction;
import summer.basic.order.OrderUtil;
import summer.ioc.IocContext;
import summer.ioc.util.IocUtil;
import summer.mvc.annotation.At;

/**
 * @author li
 * @version 1 (2015年10月13日 下午3:16:47)
 * @since Java7
 */
public class AopUtil {
    public static AopFilter[] getAopFilters(Method method, IocContext iocContext) {
        List<AopFilter> aopFilters = new ArrayList<AopFilter>();
        if (null != method.getAnnotation(At.class)) { // 类型
            aopFilters.add(IocUtil.getParameterAdapterAopFilter(iocContext));
        }
        Aop aop = method.getAnnotation(Aop.class);
        if (null != aop) {
            for (String aopBeanName : aop.value()) { // 名称
                aopFilters.add(IocUtil.getAopFilter(iocContext, aopBeanName));
            }
        }
        Transaction transaction = method.getAnnotation(Transaction.class);
        if (null != transaction) { // 名称
            aopFilters.add(IocUtil.getTransactionAopFilter(iocContext));
        }
        if (null != method.getAnnotation(At.class)) { // 类型
            aopFilters.add(IocUtil.getViewProcessorAopFilter(iocContext));
        }

        AopFilter[] filters = aopFilters.toArray(new AopFilter[aopFilters.size()]);
        filters = OrderUtil.sort(filters);

        return filters;
    }
}