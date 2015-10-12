package summer.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author li
 * @version 1 (2015年10月11日 上午10:05:36)
 * @since Java7
 */
public class Stream {
    public static FileOutputStream newFileOutputStream(String fileName) {
        try {
            return new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream newFileInputStream(String fileName) {
        try {
            return new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}