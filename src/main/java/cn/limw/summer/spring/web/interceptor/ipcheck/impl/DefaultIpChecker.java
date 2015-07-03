package cn.limw.summer.spring.web.interceptor.ipcheck.impl;

import cn.limw.summer.spring.web.interceptor.ipcheck.IpChecker;

/**
 * @author li
 * @version 1 (2015年3月18日 下午1:13:12)
 * @since Java7
 */
public class DefaultIpChecker implements IpChecker {
    public Boolean check(String ip) {
        return true;
    }
}