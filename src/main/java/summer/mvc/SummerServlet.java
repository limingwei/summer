package summer.mvc;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:49:26)
 * @since Java7
 */
public class SummerServlet implements Servlet {
    public void init(ServletConfig config) throws ServletException {}

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {}

    public String getServletInfo() {
        return null;
    }

    public void destroy() {}
}