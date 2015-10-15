package summer.aop.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import summer.aop.AopFilter;
import summer.aop.annotation.Aop;
import summer.aop.annotation.Transaction;
import summer.aop.filter.TransactionAopFilter;
import summer.basic.order.OrderUtil;
import summer.ioc.IocContext;
import summer.ioc.util.IocUtil;
import summer.mvc.ParameterAdapter;
import summer.mvc.ViewProcessor;
import summer.mvc.annotation.At;
import summer.mvc.aop.ParameterAdapterAopFilter;
import summer.mvc.aop.ViewProcessorAopFilter;
import summer.mvc.impl.SummerParameterAdapter;
import summer.mvc.impl.SummerViewProcessor;

/**
 * @author li
 * @version 1 (2015年10月13日 下午3:16:47)
 * @since Java7
 */
public class AopUtil {
    public static AopFilter[] getAopFilters(Method method, IocContext iocContext) {
        List<AopFilter> aopFilters = new ArrayList<AopFilter>();
        if (null != method.getAnnotation(At.class)) { // 类型
            aopFilters.add(AopUtil.getParameterAdapterAopFilter(iocContext));
        }
        Aop aop = method.getAnnotation(Aop.class);
        if (null != aop) {
            for (String aopBeanName : aop.value()) { // 名称
                aopFilters.add(AopUtil.getAopFilter(iocContext, aopBeanName));
            }
        }
        Transaction transaction = method.getAnnotation(Transaction.class);
        if (null != transaction) { // 名称
            aopFilters.add(AopUtil.getTransactionAopFilter(iocContext));
        }
        if (null != method.getAnnotation(At.class)) { // 类型
            aopFilters.add(AopUtil.getViewProcessorAopFilter(iocContext));
        }

        AopFilter[] filters = aopFilters.toArray(new AopFilter[aopFilters.size()]);
        filters = OrderUtil.sort(filters);

        return filters;
    }

    public static AopFilter getAopFilter(IocContext iocContext, String beanId) {
        return iocContext.getBean(AopFilter.class, beanId);
    }

    public static AopFilter getParameterAdapterAopFilter(IocContext iocContext) {
        ParameterAdapter parameterAdapter;
        if (iocContext.containsBean(IocContext.DEFAULT_PARAMETER_ADAPTER_BEAN_ID)) {
            parameterAdapter = (ParameterAdapter) iocContext.getBean(IocContext.DEFAULT_PARAMETER_ADAPTER_BEAN_ID);
        } else if (iocContext.containsBean(ParameterAdapter.class)) {
            parameterAdapter = iocContext.getBean(ParameterAdapter.class);
        } else {
            parameterAdapter = new SummerParameterAdapter(IocUtil.getConvertService(iocContext));
        }
        return new ParameterAdapterAopFilter(parameterAdapter);
    }

    public static AopFilter getViewProcessorAopFilter(IocContext iocContext) {
        ViewProcessor viewProcessor;
        if (iocContext.containsBean(IocContext.DEFAULT_VIEW_PROCESSOR_BEAN_ID)) {
            viewProcessor = (ViewProcessor) iocContext.getBean(IocContext.DEFAULT_VIEW_PROCESSOR_BEAN_ID);
        } else if (iocContext.containsBean(ViewProcessor.class)) {
            viewProcessor = iocContext.getBean(ViewProcessor.class);
        } else {
            viewProcessor = new SummerViewProcessor();
        }
        return new ViewProcessorAopFilter(viewProcessor);
    }

    public static AopFilter getTransactionAopFilter(IocContext iocContext) {
        AopFilter transactionAopFilter;
        if (iocContext.containsBean(IocContext.DEFAULT_TRANSACTION_AOP_FILTER_BEAN_ID)) {
            transactionAopFilter = (AopFilter) iocContext.getBean(IocContext.DEFAULT_TRANSACTION_AOP_FILTER_BEAN_ID);
        } else {
            transactionAopFilter = iocContext.getBean(TransactionAopFilter.class);
        }
        return transactionAopFilter;
    }
}