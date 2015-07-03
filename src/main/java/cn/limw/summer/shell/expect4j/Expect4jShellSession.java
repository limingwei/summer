package cn.limw.summer.shell.expect4j;

import java.io.ByteArrayOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.slf4j.Logger;

import cn.limw.summer.java.io.util.PipedOutputStreamUtil;
import cn.limw.summer.jsch.util.ChannelUtil;
import cn.limw.summer.jsch.util.SessionUtil;
import cn.limw.summer.shell.ShellSession;
import cn.limw.summer.util.Logs;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.Session;

/**
 * @author li
 * @version 1 (2015年3月9日 下午1:27:50)
 * @since Java7
 */
public class Expect4jShellSession implements ShellSession {
    private static final Logger log = Logs.slf4j();

    private Session session;

    private Expect4jShellServer expect4jShellServer;

    public Expect4jShellSession(Session session, Expect4jShellServer expect4jShellServer) {
        this.session = session;
        this.expect4jShellServer = expect4jShellServer;
    }

    public String shell(String script) {
        log.info("before shell [" + script + "], server=" + expect4jShellServer.getHost());
        ChannelShell channel = SessionUtil.openShellChannel(session);
        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = PipedOutputStreamUtil.newPipedOutputStream(pipedInputStream);
        channel.setInputStream(pipedInputStream);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        channel.setOutputStream(out);
        ChannelUtil.connect(channel);

        PipedOutputStreamUtil.write(pipedOutputStream, (script + "\n").getBytes());

        int num = 0;
        String result = "";
        while (true) {
            byte[] bytes = out.toByteArray();
            result = new String(bytes);
            if (result.endsWith(" ~]# ")) {
                num++;
            }
            if (num >= 1) {
                break;
            }
        }

        return result;
    }
}