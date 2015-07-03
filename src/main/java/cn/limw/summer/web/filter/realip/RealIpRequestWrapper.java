package cn.limw.summer.web.filter.realip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author li
 * @version 1 (2014年12月25日 上午9:17:03)
 * @since Java7
 */
public class RealIpRequestWrapper extends HttpServletRequestWrapper {
    private String[] remoteAddrHeaderNames;

    public RealIpRequestWrapper(HttpServletRequest request, String[] remoteAddrHeaderNames) {
        super(request);
        this.remoteAddrHeaderNames = remoteAddrHeaderNames;
    }

    public String getRemoteAddr() {
        String remote_addr = get_remote_addr(this);
        return null == remote_addr ? super.getRemoteAddr() : remote_addr;
    }

    /**
     * 获取请求者IP
     */
    public String get_remote_addr(HttpServletRequest request) {
        for (String headerName : remoteAddrHeaderNames) {
            String ip = request.getHeader(headerName);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return null;
    }
}