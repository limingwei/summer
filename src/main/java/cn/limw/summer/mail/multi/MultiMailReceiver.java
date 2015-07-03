package cn.limw.summer.mail.multi;

import java.util.ArrayList;
import java.util.List;

import cn.limw.summer.async.Handler;
import cn.limw.summer.mail.MailReceiver;
import cn.limw.summer.mail.javax.ReceiveMail;

/**
 * 组合邮件收取器
 * @author li
 * @version 1 (2014年7月5日 下午2:50:23)
 * @since Java7
 */
public class MultiMailReceiver implements MailReceiver {
    private List<MailReceiver> mailReceivers = new ArrayList<MailReceiver>();

    public void setMailReceivers(List<MailReceiver> mailReceivers) {
        this.mailReceivers = mailReceivers;
    }

    public List<MailReceiver> getMailReceivers() {
        return mailReceivers;
    }

    public void receive(Handler<ReceiveMail> handler) {
        for (MailReceiver mailReceiver : getMailReceivers()) {
            mailReceiver.receive(handler);
        }
    }
}