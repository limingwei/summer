package cn.limw.summer.shiro.web.interceptor.session.http;

import cn.limw.summer.shiro.web.interceptor.session.ShiroSessionInterceptor;

/**
 * @author li
 * @version 1 (2015年4月21日 下午3:12:48)
 * @since Java7
 */
public class ShiroHttpSessionInterceptor extends ShiroSessionInterceptor {
    public Boolean getHttpSessionMode() {
        return true;
    }
}