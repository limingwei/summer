package cn.limw.summer.mail;

import org.slf4j.Logger;

import cn.limw.summer.async.Handler;
import cn.limw.summer.mail.javax.ReceiveMail;
import cn.limw.summer.mail.util.MailUtil;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Errors;

/**
 * @author li
 * @version 1 (2014年7月4日 下午3:34:18)
 * @since Java7
 */
public class AbstractMailReceiver implements MailReceiver {
    private String protocol;

    private Integer port;

    private String host;

    private String username;

    private String password;

    private Boolean ssl = false;

    private Logger logger;

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    public String getProtocol() {
        return null != protocol ? protocol : ((null != getHost() && getHost().startsWith("pop")) ? "pop3" : "imap");
    }

    public Integer getPort() {
        return null != port ? port : ((null != getProtocol() && getProtocol().equalsIgnoreCase("pop3")) ? 995 : 143);
    }

    public String getHost() {
        return null != host ? host : MailUtil.guessReceiveHost(Asserts.noEmpty(getUsername(), "username can not be null"));
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return null == username ? "" : username;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void receive(Handler<ReceiveMail> handler) {
        throw Errors.notImplemented();
    }

    public String toString() {
        return super.toString() + "[protocol=" + getProtocol() + ", host=" + getHost() + ", port=" + getPort() + ", username=" + getUsername() + ", password=" + getPassword() + ", ssl=" + getSsl() + "]";
    }
}