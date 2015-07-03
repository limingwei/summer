package cn.limw.summer.shiro.web.filter.session;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;

import cn.limw.summer.web.filter.AbstractFilter;

/**
 * @author li
 * @version 1 (2015年1月16日 下午1:34:14)
 * @since Java7
 */
public abstract class ShiroSessionFilter extends AbstractFilter {
    public abstract Boolean getHttpSessionMode();

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Boolean httpSessionMode = getHttpSessionMode();

        ServletContext servletContext = request.getServletContext();

        ShiroHttpServletRequest shiroHttpServletRequest = new ShiroHttpServletRequest(request, servletContext, httpSessionMode);
        ShiroHttpServletResponse shiroHttpServletResponse = new ShiroHttpServletResponse(response, servletContext, shiroHttpServletRequest);

        super.doFilter(shiroHttpServletRequest, shiroHttpServletResponse, chain);
    }
}