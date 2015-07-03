package cn.limw.summer.web.filter.realip;

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
 * @version 1 (2014年7月22日 上午9:02:51)
 * @since Java7
 */
public class RealIpFilter extends AbstractFilter {
    private String[] remoteAddrHeaderNames = new String[] { "X-Real-IP", "x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP" };

    /**
     * remote-addr-header-names 默认为 "X-Real-IP", "x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP"
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        String names = filterConfig.getInitParameter("remote-addr-header-names");
        if (!StringUtil.isEmpty(names)) {
            remoteAddrHeaderNames = StringUtil.split(names, ",");
        }
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new RealIpRequestWrapper((HttpServletRequest) request, remoteAddrHeaderNames), response);
    }
}