package cn.limw.summer.spring.web.servlet.mvc.handler.mapping;

import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;
import org.springframework.web.servlet.mvc.condition.HeadersRequestCondition;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Jsons;

/**
 * @author li
 * @version 1 (2015年1月22日 下午1:09:36)
 * @since Java7
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
 * @see org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping
 * @see org.springframework.web.servlet.handler.SimpleUrlHandlerMapping
 * @see org.springframework.web.servlet.DispatcherServlet
 * @see org.springframework.web.servlet.handler.AbstractHandlerMapping
 * @see org.springframework.web.servlet.handler.AbstractHandlerMapping#getOrder()
 */
public class ExtendableHandlerMapping extends RequestMappingHandlerMapping {
    {
        setOrder(Integer.MAX_VALUE - 1);
    }

    /**
     * 取得@At注解的 RequestMappingInfo
     */
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo requestMappingInfo = super.getMappingForMethod(method, handlerType);
        if (null == requestMappingInfo) {
            requestMappingInfo = getAtMappingForMethod(method, handlerType);
        }
        return requestMappingInfo;
    }

    private RequestMappingInfo getAtMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = null;
        At methodAnnotation = AnnotationUtils.findAnnotation(method, At.class);
        if (methodAnnotation != null) {
            Map map = Asserts.noNull(AnnotationUtils.findAnnotation(handlerType, Map.class), "@Map required on type " + handlerType.getName());

            RequestCondition<?> methodCondition = getCustomMethodCondition(method);
            info = createRequestMappingInfo(methodAnnotation, methodCondition, map);
            RequestMapping typeAnnotation = AnnotationUtils.findAnnotation(handlerType, RequestMapping.class);
            if (typeAnnotation != null) {
                RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
                info = createRequestMappingInfo(typeAnnotation, typeCondition).combine(info);
            }
        }
        return info;
    }

    /**
     * 添加时候排重, 假定 ExtendableHandlerMapping 在 RequestMappingHandlerMapping 后执行
     */
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        HandlerMethod oldHandlerMethod = getHandlerMethods().get(mapping);
        if (null == oldHandlerMethod) {
            super.registerHandlerMethod(handler, method, mapping);
        }
    }

    /**
     * @see #createRequestMappingInfo(RequestMapping, RequestCondition)
     */
    private RequestMappingInfo createRequestMappingInfo(At annotation, RequestCondition<?> customCondition, Map mapAnnotation) {
        String[] patterns = resolveEmbeddedValuesInPatterns(ExtendableHandlerMappingUtil.mergeValues(annotation.value(), Jsons.toMap(mapAnnotation.value()))); // 变量

        return new RequestMappingInfo(
                new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), useSuffixPatternMatch(), useTrailingSlashMatch(), getFileExtensions()),
                new RequestMethodsRequestCondition(annotation.method()),
                new ParamsRequestCondition(annotation.params()),
                new HeadersRequestCondition(annotation.headers()),
                new ConsumesRequestCondition(annotation.consumes(), annotation.headers()),
                new ProducesRequestCondition(annotation.produces(), annotation.headers(), getContentNegotiationManager()),
                customCondition);
    }
}