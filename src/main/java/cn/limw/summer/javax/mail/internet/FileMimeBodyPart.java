package cn.limw.summer.javax.mail.internet;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeUtility;

/**
 * @author li
 * @version 1 (2015年4月22日 上午9:22:03)
 * @since Java7
 */
public class FileMimeBodyPart extends MimeBodyPart {
    public FileMimeBodyPart(File file) {
        try {
            setDataHandler(new DataHandler(new FileDataSource(file)));
            setFileName(MimeUtility.encodeWord(file.getName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}