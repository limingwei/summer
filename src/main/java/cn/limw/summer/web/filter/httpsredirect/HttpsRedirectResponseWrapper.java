package cn.limw.summer.web.filter.httpsredirect;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author li
 * @version 1 (2014年12月25日 上午9:18:43)
 * @since Java7
 */
public class HttpsRedirectResponseWrapper extends HttpServletResponseWrapper {
    private HttpServletRequest request;

    public HttpsRedirectResponseWrapper(HttpServletResponse response, HttpServletRequest request) {
        super(response);
        this.request = request;
    }

    public void sendRedirect(String location) throws IOException {
        super.sendRedirect(isInnerSiteUrl(location) ? toFullRedirectLocation(location, request) : location);
    }

    private static Boolean isInnerSiteUrl(String location) {
        return !location.startsWith("http://") && !location.startsWith("https://");
    }

    private static String toFullRedirectLocation(String location, HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + (location.startsWith("/") ? location : "/" + location);
    }
}