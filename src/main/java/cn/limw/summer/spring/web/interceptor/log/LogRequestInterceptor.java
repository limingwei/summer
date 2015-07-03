package cn.limw.summer.spring.web.interceptor.log;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import cn.limw.summer.spring.web.interceptor.doubleparameter.DoubleParameterToOneUtil;
import cn.limw.summer.spring.web.servlet.AbstractHandlerInterceptor;
import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年5月25日 下午2:22:13)
 * @since Java7
 */
public class LogRequestInterceptor extends AbstractHandlerInterceptor {
    private static final Logger log = Logs.slf4j();

    private ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<Long>();

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        THREAD_LOCAL.set(System.currentTimeMillis());
        return super.preHandle(request, response, handler);
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        long time = System.currentTimeMillis() - THREAD_LOCAL.get();
        log(request, time, handler);
    }

    public void log(HttpServletRequest request, long time, Object handler) {
        String format = "serverName={}, contextPath={}, requestURI={}, method={}, remoteAddr={}, userAgent={}, parameterMap={}, 耗时: {} ms, handler={}";
        Map<String, String[]> parameterMap = DoubleParameterToOneUtil.getParameterMap(request);
        Object[] arguments = { Mvcs.getServerName(request), request.getContextPath(), Mvcs.getRequestURI(request),//
                request.getMethod(), request.getRemoteAddr(), request.getHeader("User-Agent"), parameterMap, time, handler };

        if (time > 500) {
            log.error(format, arguments);
        } else if (time > 100) {
            log.warn(format, arguments);
        } else {
            log.info(format, arguments);
        }
    }
}