package summer.mvc.impl;

import javax.servlet.http.HttpServletRequest;

import summer.converter.ConvertService;
import summer.mvc.ParameterAdapter;

/**
 * @author li
 * @version 1 (2015年10月12日 下午4:05:01)
 * @since Java7
 */
public class SummerParameterAdapter implements ParameterAdapter {
    private ConvertService convertService;

    public Object[] adapt(HttpServletRequest request, String[] parameterNames, Class<?>[] parameterTypes) {
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < args.length; i++) {
            String value = request.getParameter(parameterNames[i]);
            args[i] = convertService.convert(String.class, parameterTypes[i], value);
        }
        return args;
    }
}