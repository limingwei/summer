package com.unblocked.support.web.cache;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * CacheFilter
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月4日 下午5:24:54)
 */
public class CacheFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {}

    public void destroy() {}
}