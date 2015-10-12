package summer.mvc.impl;

import javax.servlet.http.HttpServletRequest;

import summer.mvc.ParameterAdapter;

/**
 * @author li
 * @version 1 (2015年10月12日 下午4:05:01)
 * @since Java7
 */
public class SummerParameterAdapter implements ParameterAdapter {
    public Object[] adapt(HttpServletRequest request, String[] parameterNames, Class<?>[] parameterTypes) {
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < args.length; i++) {
            args[i] = request.getParameter(parameterNames[i]);
        }
        return args;
    }
}