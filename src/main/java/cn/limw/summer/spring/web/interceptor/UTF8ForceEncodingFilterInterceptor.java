package cn.limw.summer.spring.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.spring.web.servlet.AbstractHandlerInterceptor;

/**
 * @author li
 * @version 1 (2015年4月22日 下午2:15:51)
 * @since Java7
 */
public class UTF8ForceEncodingFilterInterceptor extends AbstractHandlerInterceptor {
    private String encoding = "UTF8";

    private Boolean forceEncoding = true;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (getEncoding() != null && (getForceEncoding() || request.getCharacterEncoding() == null)) {
            request.setCharacterEncoding(getEncoding());

            if (getForceEncoding()) {
                response.setCharacterEncoding(getEncoding());
            }
        }
        return super.preHandle(request, response, handler);
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Boolean getForceEncoding() {
        return forceEncoding;
    }

    public void setForceEncoding(Boolean forceEncoding) {
        this.forceEncoding = forceEncoding;
    }
}