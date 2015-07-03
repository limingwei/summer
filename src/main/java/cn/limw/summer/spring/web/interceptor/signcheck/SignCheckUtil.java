package cn.limw.summer.spring.web.interceptor.signcheck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.web.method.HandlerMethod;

import cn.limw.summer.java.net.util.NetUrlUtil;
import cn.limw.summer.spring.web.method.HandlerMethodUtil;
import cn.limw.summer.util.MD5Util;

/**
 * @author li
 * @version 1 (2015年1月12日 上午10:23:18)
 * @since Java7
 */
public class SignCheckUtil {
    public static SignCheck getAnnotation(Object handler) {
        if (!HandlerMethodUtil.isHandlerMethod(handler)) {
            return null;
        } else {
            SignCheck signCheck = HandlerMethodUtil.getMethodAnnotation((HandlerMethod) handler, SignCheck.class);
            if (null == signCheck) {
                signCheck = HandlerMethodUtil.getBeanTypeAnnotation((HandlerMethod) handler, SignCheck.class);
            }
            return signCheck;
        }
    }

    private static String sign(Map parameterMap) {
        Set<Entry> parameterSet = parameterMap.entrySet();
        List<String> parameters = new ArrayList<String>();
        for (Entry entry : parameterSet) {
            String key = (String) entry.getKey();
            if (!"_sign".equalsIgnoreCase(key)) {
                Object entryValue = entry.getValue();
                if (null == entryValue) {
                    //
                } else if (entryValue instanceof String[]) {
                    String[] values = (String[]) entryValue;
                    for (String value : values) {
                        parameters.add(key + "=" + value);
                    }
                } else if (entryValue instanceof String) {
                    parameters.add(key + "=" + (String) entryValue);
                } else if (entryValue instanceof Integer) {
                    parameters.add(key + "=" + (Integer) entryValue);
                } else {
                    throw new RuntimeException("未知类型 " + entryValue);
                }
            }
        }
        return sign(parameters);
    }

    public static String sign(Map parameters, String signCheckSecret) {
        parameters.put("_sign_check_secret", signCheckSecret);
        return sign(new HashMap<String, String[]>(parameters));
    }

    public static String sign(List<String> parameters) {
        Collections.sort(parameters);
        String parameterString = "";
        for (String parameter : parameters) {
            parameterString += "#" + parameter + "#";
        }
        parameterString = NetUrlUtil.encode(parameterString);
        String sign = MD5Util.toMD5Hex(parameterString);
        return sign;
    }
}