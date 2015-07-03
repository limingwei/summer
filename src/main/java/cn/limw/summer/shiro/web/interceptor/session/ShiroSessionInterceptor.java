package cn.limw.summer.shiro.web.interceptor.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;

import cn.limw.summer.spring.web.servlet.AbstractHandlerInterceptor;

/**
 * @author li
 * @version 1 (2015年4月21日 下午3:05:01)
 * @since Java7
 */
public abstract class ShiroSessionInterceptor extends AbstractHandlerInterceptor {
    public abstract Boolean getHttpSessionMode();

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Boolean httpSessionMode = getHttpSessionMode();

        ServletContext servletContext = request.getServletContext();

        ShiroHttpServletRequest shiroHttpServletRequest = new ShiroHttpServletRequest(request, servletContext, httpSessionMode);
        ShiroHttpServletResponse shiroHttpServletResponse = new ShiroHttpServletResponse(response, servletContext, shiroHttpServletRequest);

        return super.preHandle(shiroHttpServletRequest, shiroHttpServletResponse, handler);
    }
}