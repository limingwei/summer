package cn.limw.summer.mail.apache;

import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;

import cn.limw.summer.mail.AbstractMailSender;
import cn.limw.summer.mail.Mail;
import cn.limw.summer.mail.apache.builder.HtmlEmailBuilder;
import cn.limw.summer.mail.util.MailUtil;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年6月27日 下午3:54:57)
 * @since Java7
 */
public class ApacheMailSender extends AbstractMailSender {
    private static final Logger log = Logs.slf4j();

    public String send(Mail email) {
        try {
            log.info("doSend email {}", email);
            String _from = email.getFrom();
            List<InternetAddress> replyTo = MailUtil.toAddressList(email.getReplyTo());

            HtmlEmail mail = new HtmlEmailBuilder()//
                    .setDebug(getDebug())//
                    .setHostName(getHost())//
                    .setAuthentication(getUsername(), getPassword())//
                    .setCharset(getCharset())//
                    .setFrom(StringUtil.isEmpty(_from) ? getFrom() : _from)//
                    .addTo(StringUtil.split(email.getTo(), ","))//
                    .addCc(StringUtil.split(email.getCc(), ","))//
                    .addBcc(StringUtil.split(email.getBcc(), ","))//
                    .setSubject(email.getSubject())//
                    .setMsg(Asserts.noEmpty(email.getContent(), "邮件内容不可以为空"))// 
                    .setReplyTo(replyTo)//
                    .build();

            String messageId = mail.send();
            log.info("after send email {} messageId={}", email, messageId);
            return messageId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}