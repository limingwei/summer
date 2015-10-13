package summer.aop.util;

import summer.aop.AopFilter;
import summer.aop.TransactionAopFilter;
import summer.converter.ConvertService;
import summer.converter.impl.SummerConvertService;
import summer.ioc.IocContext;
import summer.mvc.ParameterAdapter;
import summer.mvc.ViewProcessor;
import summer.mvc.aop.ParameterAdapterAopFilter;
import summer.mvc.aop.ViewProcessorAopFilter;
import summer.mvc.impl.SummerParameterAdapter;
import summer.mvc.impl.SummerViewProcessor;
import summer.util.Assert;

/**
 * @author li
 * @version 1 (2015年10月13日 下午3:16:47)
 * @since Java7
 */
public class AopUtil {
    public static AopFilter getAopFilter(IocContext iocContext, String beanId) {
        AopFilter aopFilter = iocContext.getBean(AopFilter.class, beanId);
        Assert.noNull(aopFilter, "aopFilter[" + beanId + "] Bean not found");
        return aopFilter;
    }

    public static ParameterAdapterAopFilter getParameterAdapterAopFilter(IocContext iocContext) {
        if (iocContext.containsBean(ParameterAdapter.class)) {
            return new ParameterAdapterAopFilter(iocContext.getBean(ParameterAdapter.class));
        } else {
            return new ParameterAdapterAopFilter(new SummerParameterAdapter(getConvertService(iocContext)));
        }
    }

    private static ConvertService getConvertService(IocContext iocContext) {
        if (iocContext.containsBean(ConvertService.class)) {
            return iocContext.getBean(ConvertService.class);
        } else {
            return new SummerConvertService();
        }
    }

    public static ViewProcessorAopFilter getViewProcessorAopFilter(IocContext iocContext) {
        if (iocContext.containsBean(ParameterAdapter.class)) {
            return new ViewProcessorAopFilter(iocContext.getBean(ViewProcessor.class));
        } else {
            return new ViewProcessorAopFilter(new SummerViewProcessor());
        }
    }

    public static TransactionAopFilter getTransactionAopFilter(IocContext iocContext) {
        TransactionAopFilter transactionAopFilter = iocContext.getBean(TransactionAopFilter.class);
        Assert.noNull(transactionAopFilter, "transactionAopFilter Bean not found");
        return transactionAopFilter;
    }
}