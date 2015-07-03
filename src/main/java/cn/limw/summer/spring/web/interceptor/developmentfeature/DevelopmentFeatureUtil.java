package cn.limw.summer.spring.web.interceptor.developmentfeature;

import org.springframework.web.method.HandlerMethod;

import cn.limw.summer.spring.web.method.HandlerMethodUtil;

/**
 * @author li
 * @version 1 (2015年3月6日 下午1:30:49)
 * @since Java7
 */
public class DevelopmentFeatureUtil {
    public static DevelopmentFeature getAnnotation(Object handler) {
        if (!HandlerMethodUtil.isHandlerMethod(handler)) {
            return null;
        } else {
            DevelopmentFeature annotation = HandlerMethodUtil.getMethodAnnotation((HandlerMethod) handler, DevelopmentFeature.class);
            if (null == annotation) {
                annotation = HandlerMethodUtil.getBeanTypeAnnotation((HandlerMethod) handler, DevelopmentFeature.class);
            }
            return annotation;
        }
    }
}