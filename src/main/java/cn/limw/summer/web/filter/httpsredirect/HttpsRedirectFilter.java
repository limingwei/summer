package cn.limw.summer.web.filter.httpsredirect;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.web.filter.AbstractFilter;

/**
 * @author li
 * @version 1 (2014年12月25日 上午9:14:44)
 * @since Java7
 */
public class HttpsRedirectFilter extends AbstractFilter {
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, new HttpsRedirectResponseWrapper(response, request), chain);
    }
}