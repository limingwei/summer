package cn.limw.summer.java.io.writer.wrapper;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * @author li
 * @version 1 (2015年6月5日 下午6:59:57)
 * @since Java7
 */
public class PrintWriterWrapper extends PrintWriter {
    public PrintWriterWrapper(Writer out) {
        super(out);
    }
}