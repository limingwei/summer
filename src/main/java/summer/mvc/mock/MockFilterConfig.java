package summer.mvc.mock;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterConfig;

/**
 * @author li
 * @version 1 (2015年10月12日 下午10:54:09)
 * @since Java7
 */
public class MockFilterConfig implements FilterConfig {
    private MockServletContext servletContext;

    private Map<String, String> initParameters = new HashMap<String, String>();

    public MockFilterConfig() {}

    public MockFilterConfig(MockServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public MockServletContext getServletContext() {
        return this.servletContext;
    }

    public String getInitParameter(String key) {
        return initParameters.get(key);
    }

    public void setInitParameter(String key, String value) {
        initParameters.put(key, value);
    }

    public Enumeration<String> getInitParameterNames() {
        return null;
    }

    public String getFilterName() {
        return null;
    }
}