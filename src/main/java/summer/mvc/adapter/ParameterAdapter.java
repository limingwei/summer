package summer.mvc.adapter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author li
 * @version 1 (2015年10月12日 下午5:45:29)
 * @since Java7
 */
public interface ParameterAdapter {
    public Object[] adapt(HttpServletRequest request, String[] parameterNames, Class<?>[] parameterTypes);
}