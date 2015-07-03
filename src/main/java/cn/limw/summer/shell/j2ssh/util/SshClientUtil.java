package cn.limw.summer.shell.j2ssh.util;

import java.io.IOException;

import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.SshAuthenticationClient;
import com.sshtools.j2ssh.connection.ChannelEventListener;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.transport.HostKeyVerification;

/**
 * @author li
 * @version 1 (2015年3月12日 下午5:04:49)
 * @since Java7
 */
public class SshClientUtil {
    public static void connect(SshClient sshClient, String host, Integer port, HostKeyVerification hostKeyVerification) {
        try {
            sshClient.connect(host, port, hostKeyVerification);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer authenticate(SshClient sshClient, SshAuthenticationClient sshAuthenticationClient) {
        try {
            return sshClient.authenticate(sshAuthenticationClient);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionChannelClient openSessionChannel(SshClient sshClient) {
        try {
            return sshClient.openSessionChannel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionChannelClient openSessionChannel(SshClient sshClient, ChannelEventListener channelEventListener) {
        try {
            return sshClient.openSessionChannel(channelEventListener);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}