package cn.limw.summer.java.io.util;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author li
 * @version 1 (2015年3月12日 下午5:09:09)
 * @since Java7
 */
public class BufferedReaderUtil {
    public static String readLine(BufferedReader bufferedReader) {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}