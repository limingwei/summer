package cn.limw.summer.webx.context;

import cn.limw.summer.util.Asserts;

import com.alibaba.citrus.webx.context.WebxComponentsLoader;

/**
 * @author li
 * @version 1 (2015年6月9日 下午3:59:41)
 * @since Java7
 */
public class WebxComponentsContext extends AbstractWebxComponentsContext {
    public WebxComponentsLoader getLoader() {
        try {
            return super.getLoader();
        } catch (IllegalStateException e) {
            if (e.getMessage().equals("no WebxComponentsLoader set")) {
                WebxComponentsLoader loader = (WebxComponentsLoader) getServletContext().getAttribute(WebxContextLoaderListener.WEBX_COMPONENTS_LOADER);
                if (null == loader) {
                    loader = WebxContextLoaderListener.WEBX_COMPONENTS_LOADER_THREAD_LOCAL.get();
                }
                if (null == loader) {
                    return cn.limw.summer.webx.context.WebxComponentsLoader.INSTANCE;
                }
                return Asserts.noNull(loader);
            } else {
                throw e;
            }
        }
    }
}