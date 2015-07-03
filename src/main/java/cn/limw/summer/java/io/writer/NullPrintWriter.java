package cn.limw.summer.java.io.writer;

import java.io.PrintWriter;

import org.apache.commons.io.output.ByteArrayOutputStream;

/**
 * @author li
 * @version 1 (2015年6月5日 下午6:23:05)
 * @since Java7
 */
public class NullPrintWriter extends PrintWriter {
    public NullPrintWriter() {
        super(new ByteArrayOutputStream());
    }
}