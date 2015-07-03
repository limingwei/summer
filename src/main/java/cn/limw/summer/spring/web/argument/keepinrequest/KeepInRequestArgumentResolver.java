package cn.limw.summer.spring.web.argument.keepinrequest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import cn.limw.summer.util.Maps;

/**
 * @author li
 * @version 1 (2015年1月8日 下午6:40:11)
 * @since Java7
 */
public abstract class KeepInRequestArgumentResolver implements HandlerMethodArgumentResolver {
    private Boolean keepInRequest = false;

    public final Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object value = doResolveArgument(parameter, mavContainer, webRequest, binderFactory);
        if (getKeepInRequest()) {
            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
            Map<String, Object> args = (Map<String, Object>) request.getAttribute("args");
            if (null == args) {
                request.setAttribute("args", Maps.toMap(parameter.getParameterName(), value));
            } else {
                args.put(parameter.getParameterName(), value);
            }
        }
        return value;
    }

    public abstract Object doResolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception;

    public Boolean getKeepInRequest() {
        return keepInRequest;
    }

    public void setKeepInRequest(Boolean keepInRequest) {
        this.keepInRequest = keepInRequest;
    }
}