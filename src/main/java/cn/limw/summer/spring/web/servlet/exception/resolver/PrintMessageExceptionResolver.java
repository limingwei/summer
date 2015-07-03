package cn.limw.summer.spring.web.servlet.exception.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.limw.summer.slf4j.logger.AnchorLogger;
import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.spring.web.view.text.TextView;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年1月9日 下午2:05:16)
 * @since Java7
 */
public class PrintMessageExceptionResolver implements HandlerExceptionResolver {
    private static final Logger log = Logs.slf4j();

    public static final String ERROR_IN_SESSION_KEY = "PrintMessageExceptionResolver-ERROR_IN_SESSION_KEY";

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.error(ex.getClass() + ", " + ex.getMessage() + ", requestURI=" + request.getRequestURI() + ", handler=" + handler//
                + ", session=" + Mvcs.getSession() + " serverName=" + Mvcs.getServerName() + ", ip=" + Mvcs.getRemoteAddr() //
                + ", Accept=" + Mvcs.getRequestHeader("Accept") //
                + ", Accept-Encoding=" + Mvcs.getRequestHeader("Accept-Encoding") //
                + ", Accept-Language=" + Mvcs.getRequestHeader("Accept-Language") //
                + ", User-Agent=" + Mvcs.getRequestHeader("User-Agent") //
                + ", Referer=" + Mvcs.getRequestHeader("Referer"), ex);

        request.getSession().setAttribute(ERROR_IN_SESSION_KEY, ex);
        return new ModelAndView(new TextView("错误!!!<br/>请联系管理员[" + AnchorLogger.getLoggerAnchor() + "]"));
    }
}