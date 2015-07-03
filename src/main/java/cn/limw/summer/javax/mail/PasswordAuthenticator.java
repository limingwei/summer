package cn.limw.summer.javax.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author li
 * @version 1 (2014年12月15日 上午10:37:41)
 * @since Java7
 */
public class PasswordAuthenticator extends Authenticator {
    private String username;

    private String password;

    public PasswordAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}