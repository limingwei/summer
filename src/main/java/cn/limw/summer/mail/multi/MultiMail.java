package cn.limw.summer.mail.multi;

import java.util.ArrayList;
import java.util.List;

import cn.limw.summer.mail.Mail;

/**
 * 多封邮件的集合
 * @author li
 * @version 1 (2014年7月16日 下午5:45:49)
 * @since Java7
 */
public class MultiMail extends Mail {
    private static final long serialVersionUID = 6399543264018834174L;

    private List<Mail> mails = new ArrayList<Mail>();

    public void add(Mail mail) {
        mails.add(mail);
    }

    public List<Mail> getMails() {
        return mails;
    }
}