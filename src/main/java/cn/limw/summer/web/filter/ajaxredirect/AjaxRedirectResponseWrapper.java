package cn.limw.summer.web.filter.ajaxredirect;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author li
 * @version 1 (2015年1月22日 下午5:54:33)
 * @since Java7
 */
public class AjaxRedirectResponseWrapper extends HttpServletResponseWrapper {
    private static final String X_REQUESTED_WITH_HEADER_KEY = "X-Requested-With";

    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";

    private static final String X_AJAX_REDIRECT_HEADER_KEY = "X-Ajax-Redirect";

    private static final String LOCATION_HEADER_KEY = "Location";

    private static final int STATUS_CODE_312 = 312;

    private HttpServletRequest request;

    public AjaxRedirectResponseWrapper(HttpServletResponse response, HttpServletRequest request) {
        super(response);
        this.request = request;
    }

    public void sendRedirect(String location) throws IOException {
        if (XML_HTTP_REQUEST.equalsIgnoreCase(request.getHeader(X_REQUESTED_WITH_HEADER_KEY)) // 是Ajax请求
                && !"false".equalsIgnoreCase(request.getHeader(X_AJAX_REDIRECT_HEADER_KEY))) { // 未禁用: 未配置或者配置为true
            ((HttpServletResponse) getResponse()).setHeader(LOCATION_HEADER_KEY, location);
            ((HttpServletResponse) getResponse()).setStatus(STATUS_CODE_312);
        } else {
            super.sendRedirect(location);
        }
    }
}