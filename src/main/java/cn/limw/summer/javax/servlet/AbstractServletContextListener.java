package cn.limw.summer.javax.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年6月25日 下午4:44:21)
 * @since Java7
 */
public class AbstractServletContextListener implements ServletContextListener {
    private static final Logger log = Logs.slf4j();

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("contextInitialized, servletContextEvent={}", servletContextEvent);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("ServletContextEvent servletContextEvent, servletContextEvent={}", servletContextEvent);
    }
}