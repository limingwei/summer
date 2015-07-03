package cn.limw.summer.spring.mock.web;

import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

import org.springframework.mock.web.DelegatingServletOutputStream;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author li
 * @version 1 (2014年10月10日 上午11:04:26)
 * @since Java7
 */
public class MockResponse extends MockHttpServletResponse {
    private OutputStream outputStream;

    public MockResponse() {}

    public MockResponse(OutputStream outputStream) {
        setOutputStream(outputStream);
    }

    public ServletOutputStream getOutputStream() {
        if (null != outputStream) {
            return new DelegatingServletOutputStream(outputStream);
        } else {
            return super.getOutputStream();
        }
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}