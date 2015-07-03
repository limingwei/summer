package cn.limw.summer.java.io.util;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author li
 * @version 1 (2015年3月11日 上午11:34:52)
 * @since Java7
 */
public class PipedOutputStreamUtil {
    public static PipedOutputStream newPipedOutputStream(PipedInputStream pipedInputStream) {
        try {
            return new PipedOutputStream(pipedInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(PipedOutputStream pipedOutputStream, byte[] bytes) {
        try {
            pipedOutputStream.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}