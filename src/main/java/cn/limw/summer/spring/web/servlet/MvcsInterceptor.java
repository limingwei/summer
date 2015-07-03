package cn.limw.summer.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author li
 * @version 1 (2014年9月12日 上午10:27:15)
 * @since Java7
 */
public class MvcsInterceptor extends AbstractHandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Mvcs.bind(request, response);
        return super.preHandle(request, response, handler);
    }
}