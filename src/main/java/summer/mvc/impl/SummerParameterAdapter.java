package summer.mvc.impl;

import javax.servlet.http.HttpServletRequest;

import summer.converter.ConvertService;
import summer.mvc.ParameterAdapter;
import summer.util.Assert;

/**
 * @author li
 * @version 1 (2015年10月12日 下午4:05:01)
 * @since Java7
 */
public class SummerParameterAdapter implements ParameterAdapter {
    private ConvertService convertService;

    public ConvertService getConvertService() {
        return Assert.noNull(convertService, "SummerParameterAdapter.convertService 为空");
    }

    public SummerParameterAdapter() {}

    public SummerParameterAdapter(ConvertService convertService) {
        this.convertService = convertService;
    }

    public Object[] adapt(HttpServletRequest request, String[] parameterNames, Class<?>[] parameterTypes) {
        Assert.noNull(request, "request 为空");

        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < args.length; i++) {
            String value = request.getParameter(parameterNames[i]);
            args[i] = getConvertService().convert(String.class, parameterTypes[i], value);
        }
        return args;
    }
}