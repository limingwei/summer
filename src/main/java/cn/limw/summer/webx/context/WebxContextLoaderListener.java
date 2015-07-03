package cn.limw.summer.webx.context;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.springframework.web.context.ContextLoader;

import cn.limw.summer.util.Logs;

import com.alibaba.citrus.webx.context.WebxComponentsContext;

/**
 * @author li
 * @version 1 (2015年6月9日 下午4:34:15)
 * @since Java7
 */
public class WebxContextLoaderListener extends AbstractWebxContextLoaderListener {
    private static final Logger log = Logs.slf4j();

    public static final String WEBX_COMPONENTS_LOADER = "WEBX_COMPONENTS_LOADER";

    public static final ThreadLocal<WebxComponentsLoader> WEBX_COMPONENTS_LOADER_THREAD_LOCAL = new ThreadLocal<WebxComponentsLoader>();

    protected ContextLoader createContextLoader() {
        log.info("createContextLoader()");
        return super.createContextLoader();
    }

    public void contextInitialized(ServletContextEvent event) {
        log.info("contextInitialized(ServletContextEvent)");
        super.contextInitialized(event);
    }

    public void contextDestroyed(ServletContextEvent event) {
        log.info("contextDestroyed(ServletContextEvent)");
        super.contextDestroyed(event);
    }

    protected Class<? extends WebxComponentsContext> getDefaultContextClass() {
        return cn.limw.summer.webx.context.WebxComponentsContext.class;
    }
}