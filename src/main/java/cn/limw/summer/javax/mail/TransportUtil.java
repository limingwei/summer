package cn.limw.summer.javax.mail;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年4月22日 上午9:16:13)
 * @since Java7
 */
public class TransportUtil {
    private static final Logger log = Logs.slf4j();

    public static void send(MimeMessage mimeMessage) {
        if (null == mimeMessage) {
            log.warn("send() mimeMessage is null");
            return;
        } else {
            try {
                mimeMessage.saveChanges();
                Transport.send(mimeMessage);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}