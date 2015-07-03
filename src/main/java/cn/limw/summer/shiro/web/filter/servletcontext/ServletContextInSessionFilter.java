package cn.limw.summer.shiro.web.filter.servletcontext;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.web.filter.AbstractFilter;

/**
 * @author li
 * @version 1 (2015年5月18日 上午9:44:35)
 * @since Java7
 */
public class ServletContextInSessionFilter extends AbstractFilter {
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(new ServletContextInSessionHttpServletRequest(request), response, chain);
    }
}