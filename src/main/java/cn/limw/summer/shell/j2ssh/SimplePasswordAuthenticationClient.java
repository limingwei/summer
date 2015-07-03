package cn.limw.summer.shell.j2ssh;

import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;

/**
 * @author li
 * @version 1 (2015年3月11日 下午4:52:30)
 * @since Java7
 */
public class SimplePasswordAuthenticationClient extends PasswordAuthenticationClient {
    public SimplePasswordAuthenticationClient(String username, String password) {
        setUsername(username);
        setPassword(password);
    }
}