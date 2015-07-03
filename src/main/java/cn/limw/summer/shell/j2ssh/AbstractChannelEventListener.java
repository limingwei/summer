package cn.limw.summer.shell.j2ssh;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

import com.sshtools.j2ssh.connection.Channel;
import com.sshtools.j2ssh.connection.ChannelEventListener;

/**
 * @author li
 * @version 1 (2015年3月11日 下午4:57:46)
 * @since Java7
 */
public class AbstractChannelEventListener implements ChannelEventListener {
    private static final Logger log = Logs.slf4j();

    public void onChannelClose(Channel channel) {
        log.info("onChannelClose {}", channel);
    }

    public void onChannelEOF(Channel channel) {
        log.info("onChannelEOF {}", channel);
    }

    public void onChannelOpen(Channel channel) {
        log.info("onChannelOpen {}", channel);
    }

    public void onDataReceived(Channel channel, byte[] bytes) {
        log.info("onDataReceived {} {}", channel, new String(bytes));
    }

    public void onDataSent(Channel channel, byte[] bytes) {
        log.info("onDataSent {} {}", channel, new String(bytes));
    }
}