package cn.limw.summer.mail;

import cn.limw.summer.mail.util.MailUtil;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年7月4日 下午3:26:55)
 * @since Java7
 */
public abstract class AbstractMailSender implements MailSender {
    private Boolean debug = false;

    private String host;

    private String username;

    private String password;

    private String from;

    private String charset = "UTF-8";

    public String getHost() {
        String _host = null != host ? host : MailUtil.guessSendHost(getUsername());
        return _host;
    }

    public String getFrom() {
        return (!StringUtil.isEmpty(from) && !"null".equalsIgnoreCase(from)) ? from : getUsername();
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public Boolean getDebug() {
        return debug;
    }
}