package cn.limw.summer.spring.web.interceptor.doubleparameter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.limw.summer.java.collection.NiceToStringMap;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年6月10日 下午3:03:03)
 * @since Java7
 */
public class DoubleParameterToOneUtil {
    /**
     * TODO 不知道什么原因, 参数变成了逗号分割的双份,此处将其变成一份
     */
    public static String doubleParameterToOne(String value) {
        if (StringUtil.isBlank(value)) {
            return value;
        }
        int index = value.indexOf(',');
        if (index <= 0) {
            return value;
        } else {
            String a = value.substring(0, index);
            String b = value.substring(index + 1);

            if (StringUtil.equals(a, b)) {
                return a;
            }
            return value;
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String[]> getParameterMap(HttpServletRequest request) {
        Map<String, String[]> parameterMap = new HashMap<String, String[]>();

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            parameterMap.put(key, getParameterValues(request, key));
        }
        return new NiceToStringMap(Collections.unmodifiableMap(parameterMap));
    }

    public static String[] getParameterValues(HttpServletRequest request, String key) {
        String[] parameterValues = request.getParameterValues(key);
        if (null != parameterValues && parameterValues.length > 0) {
            for (int i = 0; i < parameterValues.length; i++) {
                parameterValues[i] = doubleParameterToOne(parameterValues[i]);
            }
        }
        return parameterValues;
    }
}