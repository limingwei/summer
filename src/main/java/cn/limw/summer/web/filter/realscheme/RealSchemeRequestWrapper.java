package cn.limw.summer.web.filter.realscheme;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author li
 * @version 1 (2014年12月25日 上午10:22:47)
 * @since Java7
 */
public class RealSchemeRequestWrapper extends HttpServletRequestWrapper {
    private String[] realSchemeHeaderNames;

    public RealSchemeRequestWrapper(HttpServletRequest request, String[] realSchemeHeaderNames) {
        super(request);
        this.realSchemeHeaderNames = realSchemeHeaderNames;
    }

    public String getScheme() {
        String scheme = getSchemeFromHeader(this);
        return null == scheme ? super.getScheme() : scheme;
    }

    private String getSchemeFromHeader(HttpServletRequest request) {
        for (String headerName : realSchemeHeaderNames) {
            String scheme = request.getHeader(headerName);
            if (scheme != null && !scheme.isEmpty() && !"unknown".equalsIgnoreCase(scheme)) {
                return scheme;
            }
        }
        return null;
    }
}