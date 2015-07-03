package cn.limw.summer.spring.web.interceptor.sessioncheck;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.spring.web.servlet.AbstractHandlerInterceptor;
import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2015年1月8日 下午4:31:04)
 * @since Java7
 */
public class SessionCheckInterceptor extends AbstractHandlerInterceptor {
    private String sessionName;

    private String redirectLocation;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SessionCheck sessionCheck = SessionCheckUtil.getAnnotation(handler);
        if (null == sessionCheck || !sessionCheck.check()) { // 没有注解或注解不需检查
            return true;
        } else if (null != request.getSession().getAttribute(getSessionName(sessionCheck))) {
            return true;
        } else {
            response.sendRedirect(getRedirectLocation(sessionCheck));
            return false;
        }
    }

    private String getRedirectLocation(SessionCheck sessionCheck) {
        String redirect = sessionCheck.redirect();
        return redirect.isEmpty() ? getRedirectLocation() : redirect;
    }

    private String getSessionName(SessionCheck sessionCheck) {
        String name = sessionCheck.name();
        return name.isEmpty() ? getSessionName() : name;
    }

    public String getRedirectLocation() {
        return Asserts.noEmpty(redirectLocation);
    }

    public void setRedirectLocation(String redirectLocation) {
        this.redirectLocation = redirectLocation;
    }

    public String getSessionName() {
        return Asserts.noEmpty(sessionName);
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }
}