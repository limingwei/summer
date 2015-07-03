package cn.limw.summer.spring.web.interceptor.ipcheck;

/**
 * @author li
 * @version 1 (2015年3月18日 下午1:12:43)
 * @since Java7
 */
public interface IpChecker {
    public Boolean check(String ip);
}