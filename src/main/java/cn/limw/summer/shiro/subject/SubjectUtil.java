package cn.limw.summer.shiro.subject;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2014年12月9日 下午1:48:41)
 * @since Java7
 */
public class SubjectUtil {
    private static final Logger log = Logs.slf4j();

    public static void login(Subject subject, AuthenticationToken token) {
        try {
            log.info("subject login subject={} token={}", toString(subject), token);
            subject.login(token);
        } catch (AuthenticationException e) {
            throw e;
        }
    }

    public static String toString(Subject subject) {
        if (subject instanceof WebDelegatingSubject) {
            WebDelegatingSubject sub = (WebDelegatingSubject) subject;
            return subject.toString() + " ,host=" + sub.getHost() + ", previousPrincipals=" + sub.getPreviousPrincipals() + ", principal=" + sub.getPrincipal() // 
                    + ", principals=" + sub.getPrincipals() + ", securityManager=" + sub.getSecurityManager() + ", servletRequest=" + sub.getServletRequest() + ", servletResponse=" + sub.getServletResponse();
        } else {
            return subject.toString();
        }
    }
}