package cn.limw.summer.spring.web.servlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.web.filter.AbstractFilter;

/**
 * @author li
 * @version 1 (2014年9月12日 上午10:27:32)
 * @since Java7
 */
public class MvcsFilter extends AbstractFilter {
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Mvcs.bind(request, response);
        super.doFilter(request, response, chain);
    }
}