package cn.limw.summer.spring.web.interceptor.ipcheck;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author li
 * @version 1 (2015年3月18日 下午1:15:53)
 * @since Java7
 */
public interface IpCheckFailedHandler {
    public Boolean handle(HttpServletRequest request, HttpServletResponse response, Object handler);
}