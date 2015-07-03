package cn.limw.summer.shell.expect4j;

import java.util.Hashtable;

import cn.limw.summer.shell.AbstractShellServer;
import cn.limw.summer.shell.ShellSession;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * @author li
 * @version 1 (2015年3月9日 下午1:28:44)
 * @since Java7
 */
public class Expect4jShellServer extends AbstractShellServer {
    public Expect4jShellServer(String host, Integer port, String username, String password) {
        setHost(host);
        setPort(port);
        setUsername(username);
        setPassword(password);
    }

    public ShellSession openSession() {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(getUsername(), getHost(), getPort());
            session.setUserInfo(new SimpleUserInfo(getPassword()));
            session.setPassword(getPassword());

            Hashtable<String, String> config = new Hashtable<String, String>();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
            return new Expect4jShellSession(session, this);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() + " ,host=" + getHost() + ", port=" + getPort() + ", username=" + getUsername() + ", password=" + getPassword(), e);
        }
    }
}