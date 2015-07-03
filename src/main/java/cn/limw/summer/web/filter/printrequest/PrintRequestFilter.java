package cn.limw.summer.web.filter.printrequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Maps;
import cn.limw.summer.util.StringUtil;
import cn.limw.summer.web.filter.AbstractFilter;

/**
 * @author li
 * @version 1 (2015年1月12日 下午6:11:03)
 * @since Java7
 */
public class PrintRequestFilter extends AbstractFilter {
    private static final Logger log = Logs.slf4j();

    private static final ThreadLocal<Map> REQUEST_PARAMETER_MAP_THREAD_LOCAL = new ThreadLocal<Map>();

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.trace("requestURI={}, method={}, parameters={}, headers={}", request.getRequestURI(), request.getMethod(), Maps.toString(request.getParameterMap()), Maps.toString(Mvcs.getRequestHeaders(request)));
        REQUEST_PARAMETER_MAP_THREAD_LOCAL.set(new HashMap(request.getParameterMap()));
        super.doFilter(request, response, chain);
    }

    public static Map getRequestParameterMap() {
        Map map = REQUEST_PARAMETER_MAP_THREAD_LOCAL.get();
        return Maps.isEmpty(map) ? Mvcs.getParameterMap() : map;
    }

    /**
     * TODO 不知道为什么, 第一个Filter里看到的parameters正常, 过后的parameters变成了双份, 暂权且处理如此
     */
    public static String getRequestParameter(String key) {
        Map map = getRequestParameterMap();
        Object value = map.get(key);
        if (null == value) {
            return null;
        } else if (value instanceof String) {
            return (String) value;
        } else if (value instanceof String[]) {
            return ((String[]) value)[0];
        } else {
            log.error("getRequestParameter type not expected");
            return "" + value;
        }
    }

    /**
     * getRequestParameter
     */
    public static String getRequestParameter(String... keys) {
        for (String key : keys) {
            String value = getRequestParameter(key);
            if (!StringUtil.isEmpty(value)) {
                return value;
            }
        }
        return null;
    }
}