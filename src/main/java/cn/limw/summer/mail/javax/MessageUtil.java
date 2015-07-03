package cn.limw.summer.mail.javax;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * @author li
 * @version 1 (2014年10月31日 下午5:58:27)
 * @since Java7
 */
public class MessageUtil {
    public static Flags getFlags(Message message) {
        try {
            return message.getFlags();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setFlag(Message message, Flag flag, Boolean value) {
        try {
            message.setFlag(flag, value);//直接删除, 找不回来  
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}