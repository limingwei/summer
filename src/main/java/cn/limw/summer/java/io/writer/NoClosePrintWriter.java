package cn.limw.summer.java.io.writer;

import java.io.Writer;

import cn.limw.summer.java.io.writer.wrapper.PrintWriterWrapper;

/**
 * @author li
 * @version 1 (2015年6月5日 下午6:59:23)
 * @since Java7
 */
public class NoClosePrintWriter extends PrintWriterWrapper {
    public NoClosePrintWriter(Writer out) {
        super(out);
    }

    public void close() {
        super.close();
    }
}