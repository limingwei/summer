package cn.limw.summer.mail.javax;

import java.util.Date;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;

import cn.limw.summer.javax.mail.internet.util.MimeMessageUtil;
import cn.limw.summer.mail.Mail;
import cn.limw.summer.mail.util.MailUtil;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2014年7月8日 下午12:52:40)
 * @since Java7
 */
public class ReceiveMail extends Mail {
    private static final long serialVersionUID = 1416126665880739848L;

    private static final Logger log = Logs.slf4j();

    private Message message;

    private String uid;

    public ReceiveMail(Message message) {
        this.message = message;
    }

    public String getFrom() {
        if (null == super.getFrom() && null != getMessage()) {
            super.setFrom(MailUtil.getFrom(getMessage()));
        }
        return super.getFrom();
    }

    public String getTo() {
        if (null == super.getTo() && null != getMessage()) {
            super.setTo(MailUtil.getTo(getMessage()));
        }
        return super.getTo();
    }

    public String getSubject() {
        if (null == super.getSubject() && null != getMessage()) {
            super.setSubject(MailUtil.getSubject(getMessage()));
        }
        return super.getSubject();
    }

    public String getContent() {
        if (null == super.getContent()) {
            super.setContent(MailUtil.getContent(getMessage()));
        }
        return super.getContent();
    }

    public String getUid() {
        if (null == uid) {
            uid = MailUtil.getUid(message);
        }
        return uid;
    }

    public String getCc() {
        if (null == super.getCc()) {
            super.setCc(MailUtil.getCc(getMessage()));
        }
        return super.getCc();
    }

    public String getBcc() {
        if (null == super.getBcc()) {
            super.setBcc(MailUtil.getBcc(getMessage()));
        }
        return super.getBcc();
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessageId() {
        if (getMessage() instanceof MimeMessage) {
            return MimeMessageUtil.getMessageId((MimeMessage) getMessage());
        } else {
            log.error("getMessageId() 失败 message 类型不是 MimeMessage " + getMessage().getClass());
            return "message-is-not-mime-message";
        }
    }

    public Date getSentDate() {
        if (getMessage() instanceof MimeMessage) {
            return MimeMessageUtil.getSentDate((MimeMessage) getMessage());
        } else {
            log.error("getSentDate() 失败 message 类型不是 MimeMessage " + getMessage().getClass());
            return null;
        }
    }

    public Date getReceivedDate() {
        if (getMessage() instanceof MimeMessage) {
            return MimeMessageUtil.getReceivedDate((MimeMessage) getMessage());
        } else {
            log.error("getReceivedDate() 失败 message 类型不是 MimeMessage " + getMessage().getClass());
            return null;
        }
    }

    public String getSender() {
        if (getMessage() instanceof MimeMessage) {
            return MimeMessageUtil.getSender((MimeMessage) getMessage());
        } else {
            log.error("getReceivedDate() 失败 message 类型不是 MimeMessage " + getMessage().getClass());
            return "message-is-not-mime-message";
        }
    }

    /**
     * 将邮件设置删除标记,需要配合 folder.open(Folder.READ_WRITE) 及 folder.close(true)才会真的删除
     */
    public Boolean delete() {
        try {
            log.info("setting delete flag message=" + getMessage() + ", receiveMail=" + this);
            getMessage().setFlag(Flags.Flag.DELETED, true);
            // getMessage().saveChanges(); // javax.mail.IllegalWriteException: POP3 messages are read-only
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}