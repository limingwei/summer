package cn.limw.summer.mail;

import java.io.Serializable;

import cn.limw.summer.javax.mail.internet.util.MimeUtil;

/**
 * @author li
 * @version 1 (2014年6月27日 下午4:03:30)
 * @since Java7
 */
public class Mail implements Serializable {
    private static final long serialVersionUID = 8733452456986616769L;

    private String from;

    private String to;

    private String cc;

    private String bcc;

    private String subject;

    private String content;

    private String replyTo;

    private String sender;

    public Mail() {}

    public Mail(String to, String subject, String content) {
        this(null, to, subject, content);
    }

    public Mail(String from, String to, String subject, String content) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public String getCc() {
        return cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getTo() {
        return to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getReplyTo() {
        return this.replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String toString() {
        return super.toString() + ", from=" + MimeUtil.decodeText(getFrom()) + ", to=" + MimeUtil.decodeText(getTo()) + ", subject=" + getSubject();
    }
}