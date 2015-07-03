package cn.limw.summer.spring.web.interceptor.ipcheck.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.spring.web.interceptor.ipcheck.IpCheckFailedHandler;
import cn.limw.summer.spring.web.servlet.Mvcs;

/**
 * @author li
 * @version 1 (2015年3月18日 下午1:16:56)
 * @since Java7
 */
public class DefaultIpCheckFailedHandler implements IpCheckFailedHandler {
    public Boolean handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Mvcs.write("not allowed ip");
        return false;
    }
}