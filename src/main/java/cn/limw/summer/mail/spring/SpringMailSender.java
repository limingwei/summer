package cn.limw.summer.mail.spring;

import java.util.Date;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import cn.limw.summer.mail.AbstractMailSender;
import cn.limw.summer.mail.Mail;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年10月31日 下午1:10:45)
 * @since Java7
 */
public class SpringMailSender extends AbstractMailSender {
    private JavaMailSenderImpl sender;

    private synchronized JavaMailSenderImpl getSender() {
        if (null == sender) {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl() {
                public synchronized Session getSession() {
                    Session session = super.getSession();
                    session.setDebug(getDebug());
                    return session;
                }
            };
            mailSender.setUsername(getUsername());
            mailSender.setHost(getHost());
            mailSender.setPassword(getPassword());
            sender = mailSender;
        }
        return sender;
    }

    public String send(Mail mail) {
        try {
            MimeMessage mimeMessage = getSender().createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(null == mail.getFrom() ? getFrom() : mail.getFrom());//设置发送人
            mimeMessageHelper.setTo(StringUtil.split(mail.getTo(), ","));//设置收件人
            mimeMessageHelper.setCc(StringUtil.split(mail.getCc(), ","));//设置抄送人
            mimeMessageHelper.setBcc(StringUtil.split(mail.getBcc(), ","));//设置暗送人
            mimeMessageHelper.setSentDate(new Date());//设置发送日期
            mimeMessageHelper.setSubject(mail.getSubject());//设置主题
            mimeMessageHelper.setText(mail.getContent(), true);//设置邮件内容为HTML超文本格式

            getSender().send(mimeMessage);//将邮件发送
            return mimeMessage.getMessageID();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}