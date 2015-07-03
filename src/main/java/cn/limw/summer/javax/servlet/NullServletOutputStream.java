package cn.limw.summer.javax.servlet;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * @author li
 * @version 1 (2015年6月5日 下午6:39:52)
 * @since Java7
 */
public class NullServletOutputStream extends ServletOutputStream {
    public boolean isReady() {
        return false;
    }

    public void setWriteListener(WriteListener writeListener) {}

    public void write(int b) throws IOException {}
}