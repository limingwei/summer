package cn.limw.summer.spring.web.interceptor.doubleparameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.spring.web.servlet.AbstractHandlerInterceptor;

/**
 * @author li
 * @version 1 (2015年6月12日 上午10:31:59)
 * @since Java7
 */
public class DoubleParameterToOneInterceptor extends AbstractHandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(new DoubleParameterToOneHttpServletRequest(request), response, handler);
    }
}