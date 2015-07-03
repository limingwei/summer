package cn.limw.summer.web.filter.redirectreferer;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author li
 * @version 1 (2015年3月31日 下午6:40:03)
 * @since Java7
 */
public class RedirectRefererResponseWrapper extends HttpServletResponseWrapper {
    private HttpServletRequest request;

    public RedirectRefererResponseWrapper(HttpServletResponse response, HttpServletRequest request) {
        super(response);
        this.request = request;
    }

    public void sendRedirect(String location) throws IOException {
        request.getSession().setAttribute(RedirectRefererFilter.REDIRECT_REFERER, request.getRequestURI());
        super.sendRedirect(location);
    }
}