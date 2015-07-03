package cn.limw.summer.shiro.web.interceptor.session.subject;

import cn.limw.summer.shiro.web.interceptor.session.ShiroSessionInterceptor;

/**
 * @author li
 * @version 1 (2015年4月21日 下午3:12:43)
 * @since Java7
 */
public class ShiroSubjectSessionInterceptor extends ShiroSessionInterceptor {
    public Boolean getHttpSessionMode() {
        return false;
    }
}