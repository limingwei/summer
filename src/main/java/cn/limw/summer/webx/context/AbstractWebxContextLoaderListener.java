package cn.limw.summer.webx.context;

import org.slf4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

import cn.limw.summer.util.Logs;

import com.alibaba.citrus.webx.context.WebxComponentsContext;

/**
 * @author li
 * @version 1 (2015年6月9日 下午4:33:56)
 * @since Java7
 */
public abstract class AbstractWebxContextLoaderListener extends ContextLoaderListener {
    private static final Logger log = Logs.slf4j();

    protected ContextLoader createContextLoader() {
        return cn.limw.summer.webx.context.WebxComponentsLoader.INSTANCE;
    }

    protected ContextLoader createContextLoader222() {
        WebxComponentsLoader webxComponentsLoader = new WebxComponentsLoader() {
            protected Class<? extends WebxComponentsContext> getDefaultContextClass() {
                Class<? extends WebxComponentsContext> defaultContextClass = context_loader_listener_get_default_context_class();

                if (defaultContextClass == null) {
                    defaultContextClass = super.getDefaultContextClass();
                }

                return defaultContextClass;
            }
        };

        getCurrentWebApplicationContext().getServletContext().setAttribute(WebxContextLoaderListener.WEBX_COMPONENTS_LOADER, webxComponentsLoader);
        WebxContextLoaderListener.WEBX_COMPONENTS_LOADER_THREAD_LOCAL.set(webxComponentsLoader);

        log.info("createContextLoader()={}", webxComponentsLoader);
        return webxComponentsLoader;
    }

    private Class<? extends WebxComponentsContext> context_loader_listener_get_default_context_class() {
        return getDefaultContextClass();
    }

    protected Class<? extends WebxComponentsContext> getDefaultContextClass() {
        return null;
    }
}