package summer.mvc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import summer.ioc.IocContext;
import summer.log.Logger;
import summer.mvc.impl.SummerActionInvokeService;
import summer.mvc.impl.SummerMvcContext;
import summer.util.Log;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:48:21)
 * @since Java7
 */
public class SummerFilter implements Filter {
    private static final Logger log = Log.slf4j();

    private IocContext iocContext;

    private MvcContext mvcContext;

    private ActionInvokeService actionInvokeService;

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

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Mvc.setRequest(request);
        Mvc.setResponse(response);

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        ActionHandler actionHandler = getMvcContext().getActionHandler(httpServletRequest);
        if (null != actionHandler) {
            getActionInvokeService().invokeAction(actionHandler);
        } else {
            String servletPath = httpServletRequest.getServletPath();
            String method = httpServletRequest.getMethod();

            log.info("action not found, servletPath=" + servletPath + ", method=" + method);

            httpServletResponse.getWriter().append("action not found, servletPath=" + servletPath + ", method=" + method);
            httpServletResponse.setStatus(404);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {}

    public void destroy() {}
}