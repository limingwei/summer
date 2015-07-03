package cn.limw.summer.spring.mock.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

/**
 * @author li
 * @version 1 (2015年2月26日 上午9:36:45)
 * @since Java7
 */
public class MockRequest extends MockHttpServletRequest {
    public MockRequest(ServletContext servletContext) {
        super(servletContext);
    }

    public HttpSession getSession(boolean create) {
        HttpSession session = super.getSession(create);
        if (null == session) {
            setSession(session = new MockHttpSession(getServletContext()));
        }
        return session;
    }
}