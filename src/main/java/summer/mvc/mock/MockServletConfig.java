package summer.mvc.mock;

import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * MockServletConfig
 * 
 * @author li (limingwei@mail.com)
 * @version 0.1.1 (2012-09-27)
 */
class MockServletConfig implements ServletConfig {

    private ServletContext servletContext;

    public MockServletConfig(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return this.servletContext;
    }

    public String getInitParameter(String key) {
        return null;
    }

    public Enumeration<String> getInitParameterNames() {
        return null;
    }

    public String getServletName() {
        return null;
    }
}