package cn.limw.summer.shell.j2ssh.util;

import java.io.IOException;

import cn.limw.summer.java.io.exception.IORuntimeException;

import com.sshtools.j2ssh.connection.ChannelOutputStream;
import com.sshtools.j2ssh.session.SessionChannelClient;

/**
 * @author li
 * @version 1 (2015年3月12日 下午5:03:07)
 * @since Java7
 */
public class SessionChannelUtil {
    public static void write(SessionChannelClient sessionChannelClient, String command) {
        try {
            ChannelOutputStream writer = sessionChannelClient.getOutputStream();
            writer.write((command).getBytes());
            writer.flush();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public static Boolean startShell(SessionChannelClient sessionChannelClient) {
        try {
            return sessionChannelClient.startShell();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public static void close(SessionChannelClient sessionChannelClient) {
        try {
            sessionChannelClient.close();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
}