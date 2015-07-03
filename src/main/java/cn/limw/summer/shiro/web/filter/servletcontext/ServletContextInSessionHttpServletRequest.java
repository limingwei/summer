package cn.limw.summer.shiro.web.filter.servletcontext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author li
 * @version 1 (2015年5月18日 上午9:48:44)
 * @since Java7
 */
public class ServletContextInSessionHttpServletRequest extends HttpServletRequestWrapper {
    public ServletContextInSessionHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    public ServletContext getServletContext() {
        return new ServletContextInSession(super.getServletContext());
    }
}