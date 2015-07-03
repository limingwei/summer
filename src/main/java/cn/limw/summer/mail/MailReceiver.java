package cn.limw.summer.mail;

import cn.limw.summer.async.Handler;
import cn.limw.summer.mail.javax.ReceiveMail;

/**
 * @author li
 * @version 1 (2014年6月30日 上午11:08:29)
 * @since Java7
 */
public interface MailReceiver {
    public void receive(Handler<ReceiveMail> handler);
}