package cn.limw.summer.javax.mail.internet;

import javax.mail.BodyPart;
import javax.mail.MessagingException;

/**
 * @author li
 * @version 1 (2015年4月22日 上午9:27:03)
 * @since Java7
 */
public class MimeMultipart extends AbstractMimeMultipart {
    /**
     * @param bodyPart 正文
     * @param fileMimeBodyParts 附件
     */
    public MimeMultipart(BodyPart bodyPart, FileMimeBodyParts fileMimeBodyParts) {
        try {
            // 添加正文
            addBodyPart(bodyPart);

            // 添加附件
            fileMimeBodyParts.addTo(this);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}