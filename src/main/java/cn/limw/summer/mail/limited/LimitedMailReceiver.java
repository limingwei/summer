package cn.limw.summer.mail.limited;

import cn.limw.summer.async.Handler;
import cn.limw.summer.mail.MailReceiver;
import cn.limw.summer.mail.javax.ReceiveMail;

/**
 * @author li
 * @version 1 (2015年3月25日 下午5:43:31)
 * @since Java7
 */
public class LimitedMailReceiver implements MailReceiver {
    private MailReceiver mailReceiver;

    private Integer mailReceiveCountEachTime;

    public void receive(Handler<ReceiveMail> handler) {
        getMailReceiver().receive(handler);
    }

    public LimitedMailReceiver(MailReceiver mailReceiver, Integer mailReceiveCountEachTime) {
        this.mailReceiver = mailReceiver;
        this.mailReceiveCountEachTime = mailReceiveCountEachTime;
    }

    public MailReceiver getMailReceiver() {
        return mailReceiver;
    }

    public LimitedMailReceiver setMailReceiver(MailReceiver mailReceiver) {
        this.mailReceiver = mailReceiver;
        return this;
    }

    public Integer getMailReceiveCountEachTime() {
        return mailReceiveCountEachTime;
    }

    public LimitedMailReceiver setMailReceiveCountEachTime(Integer mailReceiveCountEachTime) {
        this.mailReceiveCountEachTime = mailReceiveCountEachTime;
        return this;
    }
}