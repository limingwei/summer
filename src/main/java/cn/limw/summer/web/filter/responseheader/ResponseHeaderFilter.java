package cn.limw.summer.web.filter.responseheader;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.util.NetUtil;
import cn.limw.summer.util.StringUtil;
import cn.limw.summer.web.filter.AbstractFilter;

/**
 * @author li
 * @version 1 (2014年10月15日 上午10:35:58)
 * @since Java7
 */
public class ResponseHeaderFilter extends AbstractFilter {
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setHeader("Server-Info", serverInfo(request));
        super.doFilter(request, response, chain);
    }

    private String serverInfo(HttpServletRequest request) throws UnknownHostException {
        return "ServerName=" + request.getServerName() //
                + ", ServerPort=" + getServerPort(request) // 
                + ", ip=" + StringUtil.join(", ", NetUtil.ips()) //
                + ", HostName=" + NetUtil.getLocalHostName();
    }

    private String getServerPort(HttpServletRequest request) {
        String serverPort = request.getHeader("X-Server-Port");
        return StringUtil.isEmpty(serverPort) ? request.getServerPort() + "" : serverPort;
    }
}