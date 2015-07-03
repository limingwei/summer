package cn.limw.summer.shell.j2ssh;

import com.sshtools.j2ssh.transport.HostKeyVerification;
import com.sshtools.j2ssh.transport.TransportProtocolException;
import com.sshtools.j2ssh.transport.publickey.SshPublicKey;

/**
 * @author li
 * @version 1 (2015年3月11日 下午4:50:48)
 * @since Java7
 */
public class SimpleHostKeyVerification implements HostKeyVerification {
    private Boolean verifyHost;

    public SimpleHostKeyVerification(Boolean verifyHost) {
        this.verifyHost = verifyHost;
    }

    public boolean verifyHost(String string, SshPublicKey sshPublicKey) throws TransportProtocolException {
        return verifyHost;
    }
}