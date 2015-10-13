package summer.mvc.mock;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

/**
 * MockServletContext
 * @author li (limingwei@mail.com)
 * @version 0.1.1 (2012-09-27)
 */
public class MockServletContext implements ServletContext {
    private Map<String, Object> servletContextMap;

    public MockServletContext() {
        this.servletContextMap = new HashMap<String, Object>();
    }

    public String getRealPath(String path) {
        return "";
    }

    public Object getAttribute(String key) {
        return servletContextMap.get(key);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Enumeration<String> getAttributeNames() {
        return new Vector(servletContextMap.keySet()).elements();
    }

    public void removeAttribute(String key) {
        servletContextMap.remove(key);
    }

    public void setAttribute(String key, Object value) {
        servletContextMap.put(key, value);
    }

    public void addListener(Class<? extends EventListener> listener) {}

    public void addListener(String arg0) {}

    public <T extends EventListener> void addListener(T arg0) {}

    public <T extends Filter> T createFilter(Class<T> arg0) throws ServletException {
        return null;
    }

    public <T extends EventListener> T createListener(Class<T> arg0) throws ServletException {
        return null;
    }

    public <T extends Servlet> T createServlet(Class<T> arg0) throws ServletException {
        return null;
    }

    public void declareRoles(String... arg0) {}

    public ClassLoader getClassLoader() {
        return null;
    }

    public ServletContext getContext(String arg0) {
        return null;
    }

    public String getContextPath() {
        return null;
    }

    public int getEffectiveMajorVersion() {
        return 0;
    }

    public int getEffectiveMinorVersion() {
        return 0;
    }

    public String getInitParameter(String key) {
        return null;
    }

    public Enumeration<String> getInitParameterNames() {
        return null;
    }

    public int getMajorVersion() {
        return 0;
    }

    public String getMimeType(String arg0) {
        return null;
    }

    public int getMinorVersion() {
        return 0;
    }

    public RequestDispatcher getNamedDispatcher(String arg0) {
        return null;
    }

    public RequestDispatcher getRequestDispatcher(String arg0) {
        return null;
    }

    public URL getResource(String arg0) throws MalformedURLException {
        return null;
    }

    public InputStream getResourceAsStream(String arg0) {
        return null;
    }

    public Set<String> getResourcePaths(String arg0) {
        return null;
    }

    public String getServerInfo() {
        return null;
    }

    public Servlet getServlet(String arg0) throws ServletException {
        return null;
    }

    public String getServletContextName() {
        return null;
    }

    public Enumeration<String> getServletNames() {
        return null;
    }

    public Enumeration<Servlet> getServlets() {
        return null;
    }

    public void log(String arg0) {}

    public void log(Exception arg0, String arg1) {

    }

    public void log(String arg0, Throwable arg1) {}

    public boolean setInitParameter(String arg0, String arg1) {
        return false;
    }

    public Dynamic addServlet(String servletName, String className) {
        return null;
    }

    public Dynamic addServlet(String servletName, Servlet servlet) {
        return null;
    }

    public Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
        return null;
    }

    public ServletRegistration getServletRegistration(String servletName) {
        return null;
    }

    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        return null;
    }

    public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, String className) {
        return new MockFilterRegistrationDynamic(this, filterName, className);
    }

    public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
        return null;
    }

    public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass) {
        return null;
    }

    public FilterRegistration getFilterRegistration(String filterName) {
        return null;
    }

    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        return null;
    }

    public SessionCookieConfig getSessionCookieConfig() {
        return null;
    }

    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {}

    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        return null;
    }

    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        return null;
    }

    public JspConfigDescriptor getJspConfigDescriptor() {
        return null;
    }

    public String getVirtualServerName() {
        return null;
    }
}