package cn.limw.summer.javax.mail.internet;

import javax.mail.MessagingException;

/**
 * @author li
 * @version 1 (2015年4月22日 上午9:22:44)
 * @since Java7
 */
public class Utf8TextMimeBodyPart extends TextMimeBodyPart {
    public Utf8TextMimeBodyPart(String content) {
        try {
            setContent(content, "text/html;charset=UTF-8");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}