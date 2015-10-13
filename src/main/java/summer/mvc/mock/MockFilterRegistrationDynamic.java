package summer.mvc.mock;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;

/**
 * @author li
 * @version 1 (2015年10月13日 下午1:33:40)
 * @since Java7
 */
public class MockFilterRegistrationDynamic implements Dynamic {
    public MockFilterRegistrationDynamic(MockServletContext servletContext, String filterName, String className) {}

    public void addMappingForUrlPatterns(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... urlPatterns) {}

    public void addMappingForServletNames(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... servletNames) {}

    public Collection<String> getServletNameMappings() {
        return null;
    }

    public Collection<String> getUrlPatternMappings() {
        return null;
    }

    public String getName() {
        return null;
    }

    public String getClassName() {
        return null;
    }

    public boolean setInitParameter(String name, String value) {
        return false;
    }

    public String getInitParameter(String name) {
        return null;
    }

    public Set<String> setInitParameters(Map<String, String> initParameters) {
        return null;
    }

    public Map<String, String> getInitParameters() {
        return null;
    }

    public void setAsyncSupported(boolean isAsyncSupported) {}
}