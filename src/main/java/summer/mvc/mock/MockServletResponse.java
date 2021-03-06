package summer.mvc.mock;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

/**
 * MockServletResponse
 * 
 * @author li (limingwei@mail.com)
 * @version 0.1.1 (2012-09-27)
 */
public class MockServletResponse implements ServletResponse {
    private Locale locale;

    private String characterEncoding;

    private String contentType;

    private PrintWriter writer;

    public void setWritter(PrintWriter writer) {
        this.writer = writer;
    }

    public PrintWriter getWriter() throws IOException {
        return this.writer;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setBufferSize(int bufferSize) {}

    public void setContentLength(int contentLength) {}

    public int getBufferSize() {
        return 0;
    }

    public boolean isCommitted() {
        return false;
    }

    public void reset() {}

    public void resetBuffer() {}

    public void flushBuffer() throws IOException {}

    public void setContentLengthLong(long len) {}
}