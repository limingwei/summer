package cn.limw.summer.web.filter.redirectreferer;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.web.filter.AbstractFilter;

/**
 * @author li
 * @version 1 (2015年3月31日 下午6:39:07)
 * @since Java7
 */
public class RedirectRefererFilter extends AbstractFilter {
    public static final String REDIRECT_REFERER = "redirect-referer";

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, new RedirectRefererResponseWrapper(response, request), chain);
    }
}