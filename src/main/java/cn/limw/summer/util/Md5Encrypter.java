package cn.limw.summer.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

/**
 * MD5加密器
 * @author jianglei
 */
public final class Md5Encrypter {
    private static final Logger log = Logs.slf4j();

    private Md5Encrypter() {}

    public static String encrypt32(Object source) {
        byte[] data;
        try {
            if (null == source) {
                data = "".getBytes();
            } else if (source instanceof File) {
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
            log.error("encrypt32 error " + e, e);
            return null;
        }
        return DigestUtils.md5Hex(data);
    }

    public static String encrypt16(Object source) {
        String s = encrypt32(source);
        return s.substring(8, 24);
    }
}