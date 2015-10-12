package summer.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author li
 * @version 1 (2015年10月12日 下午3:49:33)
 * @since Java7
 */
public class Mvc {
    private static final ThreadLocal<HttpServletRequest> REQUEST_THREAD_LOCAL = new ThreadLocal<HttpServletRequest>();

    private static final ThreadLocal<HttpServletResponse> RESPONSE_THREAD_LOCAL = new ThreadLocal<HttpServletResponse>();

    public static void setRequest(ServletRequest request) {
        REQUEST_THREAD_LOCAL.set((HttpServletRequest) request);
    }

    public static void setResponse(ServletResponse response) {
        RESPONSE_THREAD_LOCAL.set((HttpServletResponse) response);
    }

    public static HttpServletRequest getRequest() {
        return REQUEST_THREAD_LOCAL.get();
    }

    public static HttpServletResponse getResponse() {
        return RESPONSE_THREAD_LOCAL.get();
    }

    public static PrintWriter getResponseWriter() {
        try {
            return getResponse().getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}