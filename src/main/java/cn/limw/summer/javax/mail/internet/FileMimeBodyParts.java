package cn.limw.summer.javax.mail.internet;

import java.io.File;
import java.util.List;

import javax.mail.MessagingException;

/**
 * @author li
 * @version 1 (2015年4月23日 上午10:09:11)
 * @since Java7
 */
public class FileMimeBodyParts {
    private List<File> files;

    public FileMimeBodyParts(List<File> files) {
        this.files = files;
    }

    public void addTo(MimeMultipart mimeMultipart) {
        for (File file : files) {
            try {
                mimeMultipart.addBodyPart(new FileMimeBodyPart(file));
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}