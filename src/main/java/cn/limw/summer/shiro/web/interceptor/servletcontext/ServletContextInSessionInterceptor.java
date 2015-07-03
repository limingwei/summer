package cn.limw.summer.shiro.web.interceptor.servletcontext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.shiro.web.filter.servletcontext.ServletContextInSessionHttpServletRequest;
import cn.limw.summer.spring.web.servlet.AbstractHandlerInterceptor;

/**
 * @author li
 * @version 1 (2015年5月18日 上午9:47:02)
 * @since Java7
 */
public class ServletContextInSessionInterceptor extends AbstractHandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(new ServletContextInSessionHttpServletRequest(request), response, handler);
    }
}