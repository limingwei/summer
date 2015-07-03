package cn.limw.summer.mail.multi;

import java.util.ArrayList;
import java.util.List;

import cn.limw.summer.mail.Mail;
import cn.limw.summer.mail.MailSender;
import cn.limw.summer.util.ArrayUtil;

/**
 * @author li
 * @version 1 (2014年7月5日 下午5:49:45)
 * @since Java7
 */
public class MultiMailSender implements MailSender {
    private MailSender mailSender;

    public String send(Mail mail) {
        if (mail instanceof MultiMail) {
            List<String> messageIds = new ArrayList<String>();
            List<Mail> mails = ((MultiMail) mail).getMails();
            for (Mail _mail : mails) {
                messageIds.add(send(_mail));
            }
            return ArrayUtil.toString(messageIds.toArray());
        } else {
            return getMailSender().send(mail);
        }
    }

    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
}