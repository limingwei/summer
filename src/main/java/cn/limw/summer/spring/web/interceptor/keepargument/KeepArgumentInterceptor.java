package cn.limw.summer.spring.web.interceptor.keepargument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.spring.web.servlet.AbstractHandlerInterceptor;
import cn.limw.summer.spring.web.servlet.Mvcs;

/**
 * @author li
 * @version 1 (2015年1月8日 下午6:16:56)
 * @since Java7
 */
public class KeepArgumentInterceptor extends AbstractHandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("parameter", Mvcs.getParameterMap(request));
        return true;
    }
}