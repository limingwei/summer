package cn.limw.summer.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;

/**
 * @author li
 * @version 1 (2015年1月12日 上午11:04:46)
 * @since Java7
 */
public class MD5Util {
    private static final Logger log = Logs.slf4j();

    public static String toMD5Hex(String datas) {
        String result = null;
        try {
            result = toMD5Hex(datas, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("" + e, e);
        }
        return result;
    }

    public static String toMD5Hex(String datas, String charset) throws UnsupportedEncodingException {
        return toMD5Hex(datas.getBytes(charset));
    }

    public static String toMD5Hex(byte[] datas) {
        return StringUtil.toHexString(toMD5(datas));
    }

    public static byte[] toMD5(byte[] datas) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("md5");
            return md.digest(datas);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}