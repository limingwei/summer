package cn.limw.summer.java.io.writer;

import java.io.IOException;
import java.io.Writer;

/**
 * @author li
 * @version 1 (2015年6月5日 下午6:24:24)
 * @since Java7
 */
public class NullWriter extends Writer {
    public void write(char[] cbuf, int off, int len) throws IOException {}

    public void flush() throws IOException {}

    public void close() throws IOException {}
}