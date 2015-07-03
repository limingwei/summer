package cn.limw.summer.spring.web.context;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

/**
 * @author li
 * @version 1 (2014年12月17日 下午5:28:17)
 * @since Java7
 */
public class AbstractServletContextAware implements ServletContextAware {
    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }
}