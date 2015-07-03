package cn.limw.summer.spring.mock.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author li
 * @version 1 (2014年10月24日 上午9:33:49)
 * @since Java7
 */
public class Mock {
    public static MockHttpServletRequest request(WebApplicationContext webApplicationContext) {
        MockHttpServletRequest request = new MockHttpServletRequest(webApplicationContext.getServletContext());
        request.setAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        request.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        return request;
    }

    public static HttpServletRequest request() {
        return new MockHttpServletRequest();
    }

    public static MockResponse response() {
        return new MockResponse();
    }
}