package cn.limw.summer.spring.web.interceptor.sessioncheck;

import org.springframework.web.method.HandlerMethod;

import cn.limw.summer.spring.web.method.HandlerMethodUtil;

/**
 * @author li
 * @version 1 (2015年1月8日 下午4:45:27)
 * @since Java7
 */
public class SessionCheckUtil {
    public static SessionCheck getAnnotation(Object handler) {
        if (HandlerMethodUtil.isHandlerMethod(handler)) {
            SessionCheck sessionCheck = HandlerMethodUtil.getMethodAnnotation((HandlerMethod) handler, SessionCheck.class);
            if (null == sessionCheck) {
                sessionCheck = HandlerMethodUtil.getBeanTypeAnnotation((HandlerMethod) handler, SessionCheck.class);
            }
            return sessionCheck;
        } else {
            return null;
        }
    }
}