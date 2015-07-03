package cn.limw.summer.javax.mail.internet.util;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年4月24日 上午9:49:50)
 * @since Java7
 */
public class MimeMessageUtil {
    public static String getMessageId(MimeMessage mimeMessage) {
        try {
            return mimeMessage.getMessageID();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date getSentDate(MimeMessage message) {
        try {
            return message.getSentDate();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date getReceivedDate(MimeMessage message) {
        try {
            return message.getReceivedDate();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSender(MimeMessage message) {
        try {
            return StringUtil.toString(message.getSender());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}