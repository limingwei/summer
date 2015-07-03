package cn.limw.summer.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * BASE64可逆算法加密器
 * @author jianglei
 */
public class Base64Encrypter {
    private static final Logger log = Logs.slf4j();

    private Base64Encrypter() {}

    public static String encrypt(Object source) {
        byte[] data;
        try {
            if (source instanceof File) {
                data = FileUtils.readFileToByteArray((File) source);
            } else if (source instanceof InputStream) {
                data = IOUtils.toByteArray((InputStream) source);
            } else if (source instanceof Reader) {
                data = IOUtils.toByteArray((Reader) source);
            } else if (source instanceof byte[]) {
                data = (byte[]) source;
            } else {
                data = source.toString().getBytes();
            }
        } catch (IOException e) {
            log.error("encrypt error source=" + source + ", e=" + e, e);
            return null;
        }
        return Base64.encode(data).replaceAll("\n", "");
    }

    public static String decrypt(String encryptedText) {
        byte[] bytes = Base64.decode(encryptedText);
        return bytes == null ? null : new String(bytes);
    }
}
