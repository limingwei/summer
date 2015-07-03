package cn.limw.summer.java.io.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import cn.limw.summer.util.Files;

/**
 * @author li
 * @version 1 (2015年6月18日 下午3:27:12)
 * @since Java7
 */
public class StreamUtil {
    public static byte[] inputStreamToByteArray(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Files.write(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}