package summer.ioc.util;

import summer.converter.ConvertService;
import summer.converter.impl.SummerConvertService;
import summer.ioc.IocContext;
import summer.mvc.ViewProcessor;

/**
 * @author li
 * @version 1 (2015年10月15日 下午1:12:31)
 * @since Java7
 */
public class IocUtil {
    public static ConvertService getConvertService(IocContext iocContext) {
        ConvertService convertService;
        if (iocContext.containsBean("convertService")) {
            convertService = (ConvertService) iocContext.getBean("convertService");
        } else if (iocContext.containsBean(ViewProcessor.class)) {
            convertService = iocContext.getBean(ConvertService.class);
        } else {
            convertService = new SummerConvertService();
        }
        return convertService;
    }
}