package cn.limw.summer.shiro.authc;

import javax.servlet.http.HttpServletRequest;

/**
 * @author li
 * @version 1 (2015年1月6日 上午10:23:20)
 * @since Java7
 */
public class UsernamePasswordToken extends AbstractUsernamePasswordToken {
    private static final long serialVersionUID = -6061679471404982058L;

    private HttpServletRequest request;

    public UsernamePasswordToken(String username, String password, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}