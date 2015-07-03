package cn.limw.summer.spring.web.interceptor;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.spring.web.servlet.AbstractHandlerInterceptor;
import cn.limw.summer.util.BoolUtil;
import cn.limw.summer.util.Maps;

/**
 * @author li
 * @version 1 (2015年4月23日 下午1:10:26)
 * @since Java7
 */
public class ServletContextAttributeInterceptor extends AbstractHandlerInterceptor {
    private Map<String, Object> servletContextAttributes;

    private static Boolean firstTime = true;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (BoolUtil.isTrue(firstTime)) {
            if (!Maps.isEmpty(getServletContextAttributes())) {
                ServletContext servletContext = request.getServletContext();
                for (Entry<String, Object> entry : getServletContextAttributes().entrySet()) {
                    servletContext.setAttribute(entry.getKey(), entry.getValue());
                }
            }
            firstTime = false;
        }

        return super.preHandle(request, response, handler);
    }

    public Map<String, Object> getServletContextAttributes() {
        return servletContextAttributes;
    }

    public void setServletContextAttributes(Map<String, Object> servletContextAttributes) {
        this.servletContextAttributes = servletContextAttributes;
    }
}