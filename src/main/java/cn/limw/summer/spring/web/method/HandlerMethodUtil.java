package cn.limw.summer.spring.web.method;

import java.lang.annotation.Annotation;

import org.springframework.web.method.HandlerMethod;

/**
 * @author li
 * @version 1 (2015年1月8日 下午4:51:15)
 * @since Java7
 */
public class HandlerMethodUtil {
    public static boolean isHandlerMethod(Object handler) {
        return handler instanceof HandlerMethod;
    }

    public static <T extends Annotation> T getMethodAnnotation(HandlerMethod handlerMethod, Class<T> annotationType) {
        return handlerMethod.getMethodAnnotation(annotationType);
    }

    public static <T extends Annotation> T getBeanTypeAnnotation(HandlerMethod handlerMethod, Class<T> annotationType) {
        return handlerMethod.getBeanType().getAnnotation(annotationType);
    }
}