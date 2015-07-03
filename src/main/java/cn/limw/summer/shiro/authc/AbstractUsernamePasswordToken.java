package cn.limw.summer.shiro.authc;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author li
 * @version 1 (2015年6月3日 下午2:14:04)
 * @since Java7
 */
public class AbstractUsernamePasswordToken extends UsernamePasswordToken {
    private static final long serialVersionUID = 5980086095926933937L;

    public AbstractUsernamePasswordToken(String username, String password, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
    }
}