package cn.limw.summer.mail.async;

import java.util.concurrent.Callable;

import cn.limw.summer.async.AsyncRunner;
import cn.limw.summer.mail.Mail;
import cn.limw.summer.mail.MailSender;

/**
 * @author li
 * @version 1 (2014年7月3日 下午2:32:00)
 * @since Java7
 */
public class AsyncMailSender extends AsyncRunner<String> implements MailSender {
    private MailSender mailSender;

    public String send(final Mail mail) {
        doTask(new Callable<String>() {
            public String call() throws Exception {
                String id = getMailSender().send(mail);
                getHandler().handle(id);
                return id;
            }
        });
        return null;
    }

    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
}