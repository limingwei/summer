package cn.limw.summer.jsch.util;

import java.util.Hashtable;

import cn.limw.summer.shell.expect4j.SimpleUserInfo;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 * @author li
 * @version 1 (2015年3月11日 上午11:28:48)
 * @since Java7
 */
public class SessionUtil {
    public static ChannelShell openShellChannel(Session session) {
        try {
            return (ChannelShell) session.openChannel("shell");
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
    }

    public static Session connect(String host, Integer port, String username, String password) {
        try {

            JSch jsch = new JSch();
            Session session = jsch.getSession(username, host, port);
            session.setPassword(password);

            UserInfo userinfo = new SimpleUserInfo(password);
            session.setUserInfo(userinfo);

            Hashtable<String, String> config = new Hashtable<String, String>();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
            return session;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}