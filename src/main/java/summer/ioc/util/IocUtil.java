package summer.ioc.util;

import summer.aop.AopFilter;
import summer.aop.filter.TransactionAopFilter;
import summer.converter.ConvertService;
import summer.converter.impl.SummerConvertService;
import summer.ioc.IocContext;
import summer.mvc.ActionInvokeService;
import summer.mvc.ActionLoader;
import summer.mvc.ParameterAdapter;
import summer.mvc.ViewProcessor;
import summer.mvc.aop.ParameterAdapterAopFilter;
import summer.mvc.aop.ViewProcessorAopFilter;
import summer.mvc.impl.SummerActionInvokeService;
import summer.mvc.impl.SummerParameterAdapter;
import summer.mvc.impl.SummerViewProcessor;
import summer.mvc.loader.AnnotationActionLoader;

/**
 * @author li
 * @version 1 (2015年10月15日 下午1:12:31)
 * @since Java7
 */
public class IocUtil {
    public static ActionLoader getActionLoader(IocContext iocContext) {
        ActionLoader actionLoader;
        if (iocContext.containsBean(ActionLoader.class, IocContext.DEFAULT_ACTION_LOADER_BEAN_ID)) {
            actionLoader = (ActionLoader) iocContext.getBean(ActionLoader.class, IocContext.DEFAULT_ACTION_LOADER_BEAN_ID);
        } else if (iocContext.containsBean(ActionLoader.class)) {
            actionLoader = iocContext.getBean(ActionLoader.class);
        } else {
            actionLoader = new AnnotationActionLoader(iocContext);
        }
        return actionLoader;
    }

    public static ActionInvokeService getActionInvokeService(IocContext iocContext) {
        ActionInvokeService actionInvokeService;
        if (iocContext.containsBean(ActionInvokeService.class, IocContext.DEFAULT_ACTION_INVOKE_SERVICE_BEAN_ID)) {
            actionInvokeService = (ActionInvokeService) iocContext.getBean(ActionInvokeService.class, IocContext.DEFAULT_ACTION_INVOKE_SERVICE_BEAN_ID);
        } else if (iocContext.containsBean(ActionInvokeService.class)) {
            actionInvokeService = iocContext.getBean(ActionInvokeService.class);
        } else {
            actionInvokeService = new SummerActionInvokeService(iocContext);
        }
        return actionInvokeService;
    }

    public static ConvertService getConvertService(IocContext iocContext) {
        ConvertService convertService;
        if (iocContext.containsBean(ConvertService.class, IocContext.DEFAULT_CONVERT_SERVICE_BEAN_ID)) {
            convertService = (ConvertService) iocContext.getBean(ConvertService.class, IocContext.DEFAULT_CONVERT_SERVICE_BEAN_ID);
        } else if (iocContext.containsBean(ConvertService.class)) {
            convertService = iocContext.getBean(ConvertService.class);
        } else {
            convertService = new SummerConvertService();
        }
        return convertService;
    }

    public static AopFilter getAopFilter(IocContext iocContext, String beanId) {
        return iocContext.getBean(AopFilter.class, beanId);
    }

    public static AopFilter getParameterAdapterAopFilter(IocContext iocContext) {
        ParameterAdapter parameterAdapter;
        if (iocContext.containsBean(AopFilter.class, IocContext.DEFAULT_PARAMETER_ADAPTER_BEAN_ID)) {
            parameterAdapter = (ParameterAdapter) iocContext.getBean(AopFilter.class, IocContext.DEFAULT_PARAMETER_ADAPTER_BEAN_ID);
        } else if (iocContext.containsBean(ParameterAdapter.class)) {
            parameterAdapter = iocContext.getBean(ParameterAdapter.class);
        } else {
            parameterAdapter = new SummerParameterAdapter(IocUtil.getConvertService(iocContext));
        }
        return new ParameterAdapterAopFilter(parameterAdapter);
    }

    public static AopFilter getViewProcessorAopFilter(IocContext iocContext) {
        ViewProcessor viewProcessor;
        if (iocContext.containsBean(AopFilter.class, IocContext.DEFAULT_VIEW_PROCESSOR_BEAN_ID)) {
            viewProcessor = (ViewProcessor) iocContext.getBean(AopFilter.class, IocContext.DEFAULT_VIEW_PROCESSOR_BEAN_ID);
        } else if (iocContext.containsBean(ViewProcessor.class)) {
            viewProcessor = iocContext.getBean(ViewProcessor.class);
        } else {
            viewProcessor = new SummerViewProcessor();
        }
        return new ViewProcessorAopFilter(viewProcessor);
    }

    public static AopFilter getTransactionAopFilter(IocContext iocContext) {
        AopFilter transactionAopFilter;
        if (iocContext.containsBean(AopFilter.class, IocContext.DEFAULT_TRANSACTION_AOP_FILTER_BEAN_ID)) {
            transactionAopFilter = (AopFilter) iocContext.getBean(AopFilter.class, IocContext.DEFAULT_TRANSACTION_AOP_FILTER_BEAN_ID);
        } else {
            transactionAopFilter = iocContext.getBean(TransactionAopFilter.class);
        }
        return transactionAopFilter;
    }
}