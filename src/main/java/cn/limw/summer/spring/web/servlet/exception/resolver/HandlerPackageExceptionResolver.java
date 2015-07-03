package cn.limw.summer.spring.web.servlet.exception.resolver;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Maps;

/**
 * @author li
 * @version 1 (2015年5月15日 上午9:04:52)
 * @since Java7
 */
public class HandlerPackageExceptionResolver implements HandlerExceptionResolver {
    private static final Logger log = Logs.slf4j();

    private HandlerExceptionResolver defaultExceptionResolver;

    private Map<String, HandlerExceptionResolver> packageExceptionResolvers;

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (handler instanceof HandlerMethod) {
            if (!Maps.isEmpty(getPackageExceptionResolvers())) {
                String beanType = ((HandlerMethod) handler).getBeanType().getName();
                for (Entry<String, HandlerExceptionResolver> entry : getPackageExceptionResolvers().entrySet()) {
                    if (beanType.startsWith(entry.getKey())) {
                        return packageExceptionResolverResolveException(entry.getValue(), request, response, handler, ex);
                    }
                }
            }
        }
        return defaultExceptionResolverResolveException(request, response, handler, ex);
    }

    private ModelAndView packageExceptionResolverResolveException(HandlerExceptionResolver handlerExceptionResolver, HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        return handlerExceptionResolver.resolveException(request, response, handler, ex);
    }

    private ModelAndView defaultExceptionResolverResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (null == getDefaultExceptionResolver()) {
            log.error("defaultResolveException() defaultExceptionResolver is null uri=" + request.getRequestURI() + ", handler=" + handler + ", ex=" + ex);
            return null;
        } else {
            return getDefaultExceptionResolver().resolveException(request, response, handler, ex);
        }
    }

    public Map<String, HandlerExceptionResolver> getPackageExceptionResolvers() {
        return packageExceptionResolvers;
    }

    public void setPackageExceptionResolvers(Map<String, HandlerExceptionResolver> packageExceptionResolvers) {
        this.packageExceptionResolvers = packageExceptionResolvers;
    }

    public HandlerExceptionResolver getDefaultExceptionResolver() {
        return defaultExceptionResolver;
    }

    public void setDefaultExceptionResolver(HandlerExceptionResolver defaultExceptionResolver) {
        this.defaultExceptionResolver = defaultExceptionResolver;
    }
}