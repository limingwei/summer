package summer.aop.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import summer.aop.AopFilter;
import summer.aop.annotation.Aop;
import summer.aop.annotation.Transaction;
import summer.aop.filter.TransactionAopFilter;
import summer.converter.ConvertService;
import summer.converter.impl.SummerConvertService;
import summer.ioc.IocContext;
import summer.mvc.ParameterAdapter;
import summer.mvc.ViewProcessor;
import summer.mvc.annotation.At;
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
    public static byte valueOf(Byte value) {
        return value == null ? 0 : value.byteValue();
    }

    public static short valueOf(Short value) {
        if (value == null)
            return 0;
        return value.shortValue();
    }

    public static int valueOf(Integer value) {
        if (value == null)
            return 0;
        return value.intValue();
    }

    public static long valueOf(Long value) {
        if (value == null)
            return 0;
        return value.longValue();
    }

    public static double valueOf(Double value) {
        if (value == null)
            return 0;
        return value.doubleValue();
    }

    public static float valueOf(Float value) {
        if (value == null)
            return 0;
        return value.floatValue();
    }

    public static boolean valueOf(Boolean value) {
        if (value == null)
            return false;
        return value.booleanValue();
    }

    public static char valueOf(Character value) {
        if (value == null)
            return 0;
        return value.charValue();
    }

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
        return aopFilters.toArray(new AopFilter[aopFilters.size()]);
    }

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