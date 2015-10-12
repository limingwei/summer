package summer.mvc.mock;

import java.util.Enumeration;

import javax.servlet.FilterConfig;

/**
 * @author li
 * @version 1 (2015年10月12日 下午10:54:09)
 * @since Java7
 */
public class MockFilterConfig implements FilterConfig {
    private MockServletContext servletContext;

    public MockFilterConfig(MockServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public MockServletContext getServletContext() {
        return this.servletContext;
    }

    public Enumeration<String> getInitParameterNames() {
        return null;
    }

    public String getFilterName() {
        return null;
    }

    public String getInitParameter(String key) {
        return null;
    }
}