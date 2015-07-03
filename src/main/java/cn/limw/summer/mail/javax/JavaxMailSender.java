package cn.limw.summer.mail.javax;

import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;

import cn.limw.summer.java.lang.SystemUtil;
import cn.limw.summer.javax.mail.PasswordAuthenticator;
import cn.limw.summer.mail.AbstractMailSender;
import cn.limw.summer.mail.Mail;
import cn.limw.summer.mail.util.MailUtil;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年7月4日 下午3:23:02)
 * @since Java7
 */
public class JavaxMailSender extends AbstractMailSender {
    private static final Logger log = Logs.slf4j();

    private static final String UTF8 = "utf-8";

    private static final String CONTENT_TYPE_HTML_UTF8 = "text/html;charset=utf-8";

    public String send(Mail email) {
        try {
            log.info("doSend email {}", email);

            Session session = prepareSession();
            MimeMessage message = prepareMessage(email, session);
            Transport.send(message);
            String messageID = message.getMessageID();
            log.info("after send email {} messageId={}", email, messageID);
            return messageID;
        } catch (Exception e) { // 用户名 发件人不匹配
            throw new RuntimeException(e.getMessage() + ", sender.username=" + getUsername() + ", mail.from=" + email.getFrom(), e);
        }
    }

    public Session prepareSession() {
        Properties properties = SystemUtil.getProperties();
        properties.put("mail.smtp.host", Asserts.noEmpty(getHost(), "sender.host不可以为空"));
        properties.put("mail.smtp.auth", "true"); // 这样才能通过验证

        Session session = Session.getInstance(properties, new PasswordAuthenticator(getUsername(), getPassword()));
        session.setDebug(getDebug());
        return session;
    }

    public MimeMessage prepareMessage(Mail mail, Session session) throws MessagingException, AddressException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(getMailFrom(mail, this)));

        MailUtil.addRecipients(message, RecipientType.TO, StringUtil.split(mail.getTo(), ","));
        MailUtil.addRecipients(message, RecipientType.CC, StringUtil.split(mail.getCc(), ","));
        MailUtil.addRecipients(message, RecipientType.BCC, StringUtil.split(mail.getBcc(), ","));

        message.setSubject(mail.getSubject(), UTF8);
        message.setContent(mail.getContent(), CONTENT_TYPE_HTML_UTF8);
        message.setReplyTo(MailUtil.toAddressArray(mail.getReplyTo()));

        if (!StringUtil.isEmpty(mail.getSender())) {
            message.setSender(new InternetAddress(mail.getSender()));
        }

        message.saveChanges();
        return message;
    }

    private static String getMailFrom(Mail mail, JavaxMailSender javaxMailSender) {
        String from = mail.getFrom();
        return (StringUtil.isEmpty(from) || "null".equalsIgnoreCase(from)) ? javaxMailSender.getFrom() : from;
    }
}