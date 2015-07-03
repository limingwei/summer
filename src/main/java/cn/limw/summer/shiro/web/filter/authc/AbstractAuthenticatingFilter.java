package cn.limw.summer.shiro.web.filter.authc;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;

import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2014年12月5日 下午4:41:07)
 * @since Java7
 */
public abstract class AbstractAuthenticatingFilter extends AuthenticatingFilter {
    private static final Logger log = Logs.slf4j();

    public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Mvcs.bind(request, response);
        super.doFilterInternal(request, response, chain);
    }

    public void doRredirectToLogin(ServletRequest request, ServletResponse response) {
        try {
            redirectToLogin(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void doIssueSuccessRedirect(ServletRequest request, ServletResponse response) {
        try {
            issueSuccessRedirect(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doIssueRedirect(ServletRequest request, ServletResponse response, String failureUrl) {
        try {
            WebUtils.issueRedirect(request, response, failureUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
            IllegalStateException illegalStateException = new IllegalStateException(msg);
            log.error("" + illegalStateException, illegalStateException);
            throw illegalStateException;
        }
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            log.error("" + e, e);
            return onLoginFailure(token, e, request, response);
        }
    }
}