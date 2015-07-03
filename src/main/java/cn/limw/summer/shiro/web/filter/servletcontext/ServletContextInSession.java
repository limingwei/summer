package cn.limw.summer.shiro.web.filter.servletcontext;

import java.util.Enumeration;

import javax.servlet.ServletContext;

import cn.limw.summer.javax.servlet.http.wrapper.ServletContextHttpSessionWrapper;

/**
 * @author li
 * @version 1 (2015年5月18日 上午9:54:32)
 * @since Java7
 */
public class ServletContextInSession extends ServletContextHttpSessionWrapper {
    public ServletContextInSession(ServletContext servletContext) {
        super(servletContext);
    }

    public Object getAttribute(String name) {
        return super.getAttribute(name);
    }

    public void setAttribute(String name, Object object) {
        super.setAttribute(name, object);
    }

    public Enumeration<String> getAttributeNames() {
        return super.getAttributeNames();
    }

    public void removeAttribute(String name) {
        super.removeAttribute(name);
    }
}