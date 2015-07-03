package cn.limw.summer.spring.web.servlet;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import cn.limw.summer.spring.web.view.text.TextView;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Maps;
import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2014年6月26日 下午1:45:26)
 * @since Java7
 */
public class Dispatcher extends AbstractDispatcherServlet {
    private static final Logger log = Logs.slf4j();

    private static final long serialVersionUID = -7574796871690301081L;

    private List<ViewResolver> _viewResolvers;

    protected View resolveViewName(String viewName, Map<String, Object> model, Locale locale, HttpServletRequest request) throws Exception {
        request.setAttribute("Dispatcher.resolveViewName", viewName);

        for (ViewResolver viewResolver : getViewResolvers()) {
            View view = viewResolver.resolveViewName(viewName, locale);
            if (view != null) {
                return view;
            }
        }
        ServletException e = new ServletException("Could not resolve view with name '" + viewName + "' in servlet with name '" + getServletName() + "' , requestURI=" + request.getRequestURI());
        log.error("resolveViewName error viewName=" + viewName + ", " + e, e);
        return new TextView("### Dispatcher.resolveViewName() : view not found '" + viewName + "' with name '" + getServletName() + "', requestURI=" + request.getRequestURI());
    }

    public List<ViewResolver> getViewResolvers() {
        if (null == this._viewResolvers) {
            this._viewResolvers = (List<ViewResolver>) Mirrors.getFieldValue(DispatcherServlet.class, "viewResolvers", this);
        }
        return this._viewResolvers;
    }

    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            super.doDispatch(request, response);
        } catch (Exception e) {
            throw new RuntimeException("url=" + Mvcs.getRequestURI(request) + ", args=" + Maps.toString(request.getParameterMap()), e);
        }
    }
}