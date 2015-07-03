package cn.limw.summer.javax.servlet.http.wrapper;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

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
 * @author li
 * @version 1 (2015年5月18日 上午9:56:54)
 * @since Java7
 */
public class ServletContextHttpSessionWrapper implements ServletContext {
    private ServletContext servletContext;

    public ServletContextHttpSessionWrapper(ServletContext servletContext) {
        setServletContext(servletContext);
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public String getContextPath() {
        return getServletContext().getContextPath();
    }

    public ServletContext getContext(String uripath) {
        return getServletContext().getContext(uripath);
    }

    public int getMajorVersion() {
        return getServletContext().getMajorVersion();
    }

    public int getMinorVersion() {
        return getServletContext().getMinorVersion();
    }

    public int getEffectiveMajorVersion() {
        return getServletContext().getEffectiveMajorVersion();
    }

    public int getEffectiveMinorVersion() {
        return getServletContext().getEffectiveMinorVersion();
    }

    public String getMimeType(String file) {
        return getServletContext().getMimeType(file);
    }

    public Set<String> getResourcePaths(String path) {
        return getServletContext().getResourcePaths(path);
    }

    public URL getResource(String path) throws MalformedURLException {
        return getServletContext().getResource(path);
    }

    public InputStream getResourceAsStream(String path) {
        return getServletContext().getResourceAsStream(path);
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return getServletContext().getRequestDispatcher(path);
    }

    public RequestDispatcher getNamedDispatcher(String name) {
        return getServletContext().getNamedDispatcher(name);
    }

    public Servlet getServlet(String name) throws ServletException {
        return getServletContext().getServlet(name);
    }

    public Enumeration<Servlet> getServlets() {
        return getServletContext().getServlets();
    }

    public Enumeration<String> getServletNames() {
        return getServletContext().getServletNames();
    }

    public void log(String msg) {
        getServletContext().log(msg);
    }

    public void log(Exception exception, String msg) {
        getServletContext().log(exception, msg);
    }

    public void log(String message, Throwable throwable) {
        getServletContext().log(message, throwable);
    }

    public String getRealPath(String path) {
        return getServletContext().getRealPath(path);
    }

    public String getServerInfo() {
        return getServletContext().getServerInfo();
    }

    public String getInitParameter(String name) {
        return getServletContext().getInitParameter(name);
    }

    public Enumeration<String> getInitParameterNames() {
        return getServletContext().getInitParameterNames();
    }

    public boolean setInitParameter(String name, String value) {
        return getServletContext().setInitParameter(name, value);
    }

    public Object getAttribute(String name) {
        return getServletContext().getAttribute(name);
    }

    public Enumeration<String> getAttributeNames() {
        return getServletContext().getAttributeNames();
    }

    public void setAttribute(String name, Object object) {
        getServletContext().setAttribute(name, object);
    }

    public void removeAttribute(String name) {
        getServletContext().removeAttribute(name);
    }

    public String getServletContextName() {
        return getServletContext().getServletContextName();
    }

    public Dynamic addServlet(String servletName, String className) {
        return getServletContext().addServlet(servletName, className);
    }

    public Dynamic addServlet(String servletName, Servlet servlet) {
        return getServletContext().addServlet(servletName, servlet);
    }

    public Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
        return getServletContext().addServlet(servletName, servletClass);
    }

    public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
        return getServletContext().createServlet(clazz);
    }

    public ServletRegistration getServletRegistration(String servletName) {
        return getServletContext().getServletRegistration(servletName);
    }

    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        return getServletContext().getServletRegistrations();
    }

    public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, String className) {
        return getServletContext().addFilter(filterName, className);
    }

    public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
        return getServletContext().addFilter(filterName, filter);
    }

    public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass) {
        return getServletContext().addFilter(filterName, filterClass);
    }

    public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
        return getServletContext().createFilter(clazz);
    }

    public FilterRegistration getFilterRegistration(String filterName) {
        return getServletContext().getFilterRegistration(filterName);
    }

    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        return getServletContext().getFilterRegistrations();
    }

    public SessionCookieConfig getSessionCookieConfig() {
        return getServletContext().getSessionCookieConfig();
    }

    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
        getServletContext().setSessionTrackingModes(sessionTrackingModes);
    }

    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        return getServletContext().getDefaultSessionTrackingModes();
    }

    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        return getServletContext().getEffectiveSessionTrackingModes();
    }

    public void addListener(String className) {
        getServletContext().addListener(className);
    }

    public <T extends EventListener> void addListener(T t) {
        getServletContext().addListener(t);
    }

    public void addListener(Class<? extends EventListener> listenerClass) {
        getServletContext().addListener(listenerClass);
    }

    public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
        return getServletContext().createListener(clazz);
    }

    public JspConfigDescriptor getJspConfigDescriptor() {
        return getServletContext().getJspConfigDescriptor();
    }

    public ClassLoader getClassLoader() {
        return getServletContext().getClassLoader();
    }

    public void declareRoles(String... roleNames) {
        getServletContext().declareRoles(roleNames);
    }

    public String getVirtualServerName() {
        return getServletContext().getVirtualServerName();
    }
}