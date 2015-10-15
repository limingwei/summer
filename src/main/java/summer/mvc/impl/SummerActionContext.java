package summer.mvc.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import summer.ioc.IocContext;
import summer.ioc.util.IocUtil;
import summer.mvc.ActionContext;
import summer.mvc.ActionHandler;
import summer.mvc.ActionLoader;

/**
 * @author li
 * @version 1 (2015年10月12日 下午3:34:29)
 * @since Java7
 */
public class SummerActionContext implements ActionContext {
    private Map<String, ActionHandler> actionHandlerMapping;

    private IocContext iocContext;

    private ActionLoader actionLoader;

    public Map<String, ActionHandler> getActionHandlerMapping() {
        if (null == actionHandlerMapping) {
            actionHandlerMapping = getActionLoader().getActionHandlerMapping();
        }
        return actionHandlerMapping;
    }

    /**
     * synchronized
     */
    public synchronized ActionLoader getActionLoader() {
        if (null == actionLoader) {
            actionLoader = IocUtil.getActionLoader(getIocContext());
        }
        return actionLoader;
    }

    public IocContext getIocContext() {
        return iocContext;
    }

    public SummerActionContext(IocContext iocContext) {
        this.iocContext = iocContext;
    }

    public ActionHandler getActionHandler(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String method = request.getMethod();
        String key = servletPath + "@" + method;
        ActionHandler actionHandler = getActionHandlerMapping().get(key);
        return actionHandler;
    }
}