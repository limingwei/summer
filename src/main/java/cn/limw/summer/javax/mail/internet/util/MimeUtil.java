package cn.limw.summer.javax.mail.internet.util;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.MimeUtility;

import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年4月27日 上午9:46:12)
 * @since Java7
 */
public class MimeUtil {
    /**
     * 编码
     */
    public static String encodeText(String text) {
        try {
            return StringUtil.isEmpty(text) ? text : MimeUtility.encodeText(text);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解码
     */
    public static String decodeText(String text) {
        try {
            return StringUtil.isEmpty(text) ? text : MimeUtility.decodeText(text);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}