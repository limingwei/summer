package cn.limw.summer.shiro.web.filter.session.subject;

import cn.limw.summer.shiro.web.filter.session.ShiroSessionFilter;

/**
 * @author li
 * @version 1 (2015年1月16日 下午1:34:14)
 * @since Java7
 */
public class ShiroSubjectSessionFilter extends ShiroSessionFilter {
    public Boolean getHttpSessionMode() {
        return false;
    }
}