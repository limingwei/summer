package cn.limw.summer.mail.mock;

import org.slf4j.Logger;

import cn.limw.summer.mail.Mail;
import cn.limw.summer.mail.MailSender;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2014年7月1日 下午6:08:57)
 * @since Java7
 */
public class MockMailSender implements MailSender {
    private static final Logger log = Logs.slf4j();

    public String send(Mail email) {
        log.info("mock sender {} sending email {}", this, email);
        log.info("from {} to {} title {}", email.getFrom(), email.getTo(), email.getSubject());
        log.info(email.getContent());
        return null;
    }
}