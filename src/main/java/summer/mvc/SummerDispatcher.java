package summer.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import summer.ioc.IocContext;
import summer.log.Logger;
import summer.mvc.impl.SummerActionInvokeService;
import summer.mvc.impl.SummerMvcContext;
import summer.util.Log;

/**
 * @author li
 * @version 1 (2015年10月12日 下午8:56:07)
 * @since Java7
 */
public class SummerDispatcher {
    private static final Logger log = Log.slf4j();

    private IocContext iocContext;

    private MvcContext mvcContext;

    private ActionInvokeService actionInvokeService;

    public SummerDispatcher(IocContext iocContext) {
        this.iocContext = iocContext;
    }

    public IocContext getIocContext() {
        return iocContext;
    }

    public MvcContext getMvcContext() {
        if (null == mvcContext) {
            mvcContext = new SummerMvcContext(getIocContext());
        }
        return mvcContext;
    }

    public ActionInvokeService getActionInvokeService() {
        if (null == actionInvokeService) {
            actionInvokeService = new SummerActionInvokeService(getIocContext());
        }
        return actionInvokeService;
    }

    public Boolean doDispatch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Mvc.setRequest(request);
        Mvc.setResponse(response);

        ActionHandler actionHandler = getMvcContext().getActionHandler(request);
        if (null != actionHandler) {
            getActionInvokeService().invokeAction(actionHandler);
            return true;
        } else {
            String servletPath = request.getServletPath();
            String method = request.getMethod();

            log.info("Action not found, servletPath={}, method={}", servletPath, method);
            return false;
        }
    }
}