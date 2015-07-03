package cn.limw.summer.spring.web.interceptor.doubleparameter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import cn.limw.summer.util.ArrayUtil;

/**
 * @author li
 * @version 1 (2015年6月12日 上午10:35:15)
 * @since Java7
 */
public class DoubleParameterToOneHttpServletRequest extends HttpServletRequestWrapper {
    public DoubleParameterToOneHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    public String[] getParameterValues(String key) {
        return DoubleParameterToOneUtil.getParameterValues(this, key);
    }

    public String getParameter(String name) {
        return ArrayUtil.first(getParameterValues(name));
    }

    public Map<String, String[]> getParameterMap() {
        return DoubleParameterToOneUtil.getParameterMap(this);
    }
}