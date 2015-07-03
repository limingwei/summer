package cn.limw.summer.mail.javax;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import cn.limw.summer.java.lang.SystemUtil;
import cn.limw.summer.mail.AbstractMailReceiver;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.NetUtil;

/**
 * @author li
 * @version 1 (2015年3月19日 上午11:37:59)
 * @since Java7
 */
public class AbstractJavaxMailReceiver extends AbstractMailReceiver {
    private Boolean deleteMails = false;

    private Integer connectStoreTimeout = 15;

    public Integer getConnectStoreTimeout() {
        return connectStoreTimeout;
    }

    public void setConnectStoreTimeout(Integer connectStoreTimeout) {
        this.connectStoreTimeout = connectStoreTimeout;
    }

    public Boolean getDeleteMails() {
        return deleteMails;
    }

    public void setDeleteMails(Boolean deleteMails) {
        this.deleteMails = deleteMails;
    }

    public int getMode() {
        return getDeleteMails() ? Folder.READ_WRITE : Folder.READ_ONLY;
    }

    public void folderOpen(int mode, Folder inbox) throws MessagingException {
        inbox.open(mode);
    }

    public Folder storeGetFolder(Store store, String name) throws MessagingException {
        return store.getFolder(name);
    }

    public Session preapreSession() {
        Properties properties = SystemUtil.getProperties();

        if (getSsl()) {
            properties.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.pop3.socketFactory.fallback", "false");
        }

        properties.setProperty("mail.pop3.port", getPort() + "");
        properties.setProperty("mail.pop3.socketFactory.port", getPort() + "");
        properties.setProperty("mail.store.protocol", getProtocol());
        properties.setProperty("mail.stmp.timeout", "" + (10 * 1000)); // mail.stmp.timeout int I/O连接超时时间，单位为毫秒，默认为永不超时

        return Session.getInstance(properties);
    }

    public boolean checkConfig() {
        String host = Asserts.noEmpty(getHost(), "host can not be null");
        if (!NetUtil.reachable(host, getPort())) {
            getLogger().error("mail receive server not reachable host={}, port={}", getHost(), getPort());
            return false;
        }
        return true;
    }
}