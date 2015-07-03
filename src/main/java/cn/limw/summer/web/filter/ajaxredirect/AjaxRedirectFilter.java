package cn.limw.summer.web.filter.ajaxredirect;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.web.filter.AbstractFilter;

/**
 * @author li
 * @version 1 (2015年1月22日 下午5:53:15)
 * @since Java7
 */
public class AjaxRedirectFilter extends AbstractFilter {
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, new AjaxRedirectResponseWrapper(response, request), chain);
    }
}