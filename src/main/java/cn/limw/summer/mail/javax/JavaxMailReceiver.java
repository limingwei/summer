package cn.limw.summer.mail.javax;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;

import cn.limw.summer.async.Handler;
import cn.limw.summer.javax.mail.FolderUtil;
import cn.limw.summer.javax.mail.StoreUtil;
import cn.limw.summer.javax.mail.exception.AuthenticationFailedRuntimeException;

/**
 * @author li
 * @version 1 (2014年7月4日 下午3:34:40)
 * @since Java7
 */
public class JavaxMailReceiver extends AbstractJavaxMailReceiver {
    public void receive(Handler<ReceiveMail> handler) {
        try {
            if (checkConfig()) {
                doReceive(handler);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void doReceive(Handler<ReceiveMail> handler) throws Exception {
        Session session = preapreSession();
        Store store = connectStore(session, getProtocol(), getHost(), getPort(), getUsername(), getPassword());
        if (null == store) {
            return;
        }

        int mode = getMode();
        Folder inbox = storeGetFolder(store, "INBOX");
        folderOpen(mode, inbox);
        getLogger().info("opened inbox {}, mode={}, 未读邮件 {} 封, 新邮件 {} 封, 已删除邮件 {} 封", getUsername(), FolderUtil.modeToString(mode), inbox.getUnreadMessageCount(), inbox.getNewMessageCount(), inbox.getDeletedMessageCount());

        FetchProfile profile = new FetchProfile();
        profile.add(UIDFolder.FetchProfileItem.UID);

        Message[] messages = inbox.getMessages();
        getLogger().info("{} messages to be fetch in {}", messages.length, getUsername());
        inbox.fetch(messages, profile);
        getLogger().info("fetched Messages {}, 开始接收并处理邮件", getUsername());

        doHandleMessages(handler, messages);

        inbox.close(getDeleteMails());
        store.close();
        getLogger().info("结束接收并处理邮件 inbox.close(deleteMails=" + getDeleteMails() + ") && store.close()");
    }

    public void doHandleMessages(Handler<ReceiveMail> handler, Message[] messages) {
        for (Message message : messages) {
            ReceiveMail receiveMail = new ReceiveMail(message);
            try {
                doHandleMessage(handler, receiveMail);
            } catch (Throwable e) {
                getLogger().error("doHandleMessage error ", e);
            }
        }
    }

    public void doHandleMessage(Handler<ReceiveMail> handler, ReceiveMail receiveMail) {
        handler.handle(receiveMail);
    }

    public Store connectStore(Session session, String protocol, String host, Integer port, String username, String password) {
        if (null == getConnectStoreTimeout() || getConnectStoreTimeout() < 0) {
            return doConnectStore(session, protocol, host, port, username, password);
        } else {
            return localTimeoutConnectStore(session, protocol, host, port, username, password);
        }
    }

    public Store doConnectStore(Session session, String protocol, String host, Integer port, String username, String password) {
        return StoreUtil.connect(session, protocol, host, port, username, password);
    }

    public Store localTimeoutConnectStore(final Session session, final String protocol, final String host, final Integer port, final String username, final String password) {
        try {
            ExecutorService executorService = Executors.newSingleThreadExecutor();

            FutureTask<Store> futureTask = new FutureTask<Store>(new Callable<Store>() {
                public Store call() throws Exception {
                    return doConnectStore(session, protocol, host, port, username, password);
                }
            });

            executorService.submit(futureTask);

            return futureTask.get(getConnectStoreTimeout(), TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            getLogger().error("connectStore timeout connectStoreTimeout=" + getConnectStoreTimeout() + ", this=" + this + ", protocol=" + protocol + ", host=" + host + ", port=" + port + ", username=" + username + ", password=" + password + ", ssl=" + getSsl());
            return null;
        } catch (AuthenticationFailedRuntimeException e) {
            whenAuthenticationFailed(e);
            return null;
        } catch (Throwable e) {
            getLogger().error("connectStore error connectStoreTimeout=" + getConnectStoreTimeout() + ", this=" + this + ", protocol=" + protocol + ", host=" + host + ", port=" + port + ", username=" + username + ", password=" + password + ", ssl=" + getSsl(), e);
            return null;
        }
    }

    /**
     * 每隔10次打异常堆栈到日志
     */
    protected void whenAuthenticationFailed(AuthenticationFailedRuntimeException e) {
        Integer connectStoreTimeout = getConnectStoreTimeout();
        String protocol = getProtocol();
        String host = getHost();
        Integer port = getPort();
        String username = getUsername();
        Boolean ssl = getSsl();

        getLogger().error("AuthenticationFailedRuntimeException, this=" + this + ", connectStoreTimeout=" + connectStoreTimeout + ", protocol=" + protocol + ", host=" + host + ", port=" + port + ", username=" + username + ", ssl=" + ssl, e);
    }
}