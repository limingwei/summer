package cn.limw.summer.jsch.util;

import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @author li
 * @version 1 (2015年3月11日 上午11:32:34)
 * @since Java7
 */
public class ChannelUtil {
    public static void connect(ChannelShell channel) {
        try {
            channel.connect();
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
    }

    public static ChannelShell connectShellChannel(Session session, InputStream in, OutputStream out) {
        try {
            ChannelShell channel = (ChannelShell) session.openChannel("shell");
            channel.setInputStream(in);
            channel.setOutputStream(out);
            channel.connect();
            return channel;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}