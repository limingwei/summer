package cn.limw.summer.mail;

/**
 * @author li
 * @version 1 (2014年6月27日 下午3:54:09)
 * @since Java7
 * @see org.springframework.mail.javamail.JavaMailSenderImpl
 */
public interface MailSender {
    /**
     * @return the message id of the underlying MimeMessage
     */
    public String send(Mail mail);
}