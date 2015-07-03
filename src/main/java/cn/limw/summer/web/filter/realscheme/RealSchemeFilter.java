package cn.limw.summer.web.filter.realscheme;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.util.StringUtil;
import cn.limw.summer.web.filter.AbstractFilter;

/**
 * @author li
 * @version 1 (2014年12月25日 上午10:19:47)
 * @since Java7
 */
public class RealSchemeFilter extends AbstractFilter {
    private String[] realSchemeHeaderNames = new String[] { "X-Real-Scheme" };

    public void init(FilterConfig filterConfig) throws ServletException {
        String names = filterConfig.getInitParameter("real-scheme-header-names");
        if (!StringUtil.isEmpty(names)) {
            realSchemeHeaderNames = StringUtil.split(names, ",");
        }
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new RealSchemeRequestWrapper((HttpServletRequest) request, realSchemeHeaderNames), response);
    }
}