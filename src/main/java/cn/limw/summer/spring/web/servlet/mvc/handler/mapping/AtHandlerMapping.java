package cn.limw.summer.spring.web.servlet.mvc.handler.mapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

/**
 * @author li
 * @version 1 (2015年5月14日 上午11:08:03)
 * @since Java7
 * @see cn.limw.summer.spring.web.servlet.mvc.handler.mapping.FixableRequestMappingHandlerMapping
 * @see cn.limw.summer.spring.web.servlet.mvc.handler.mapping.ExtendableHandlerMapping
 */
public class AtHandlerMapping extends RequestMappingHandlerMapping {
    private Map<String, String> prefixForPackage = new HashMap<String, String>();

    /**
     * 取得@At注解的 RequestMappingInfo
     */
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        return getAtMappingForMethod(method, handlerType);
    }

    private RequestMappingInfo getAtMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = null;
        At methodAnnotation = AnnotationUtils.findAnnotation(method, At.class);
        if (methodAnnotation != null) {
            RequestCondition<?> methodCondition = getCustomMethodCondition(method);
            info = createRequestMappingInfo(methodAnnotation, methodCondition, handlerType);
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

    private String[] addPrefix(String[] patterns, String prefix) {
        String[] result = new String[patterns.length];
        for (int i = 0; i < patterns.length; i++) {
            result[i] = prefix + "" + patterns[i];
        }
        return result;
    }

    /**
     * @param handlerType
     * @see #createRequestMappingInfo(RequestMapping, RequestCondition)
     */
    private RequestMappingInfo createRequestMappingInfo(At annotation, RequestCondition<?> customCondition, Class<?> handlerType) {
        String[] patterns = resolveEmbeddedValuesInPatterns(annotation.value()); // 变量

        if (!getPrefixForPackage().isEmpty()) {
            String handler = handlerType.getName();
            Set<Entry<String, String>> entrySet = getPrefixForPackage().entrySet();
            for (Entry<String, String> entry : entrySet) {
                if (handler.startsWith(entry.getKey())) {
                    patterns = addPrefix(patterns, entry.getValue());
                }
            }
        }

        return new RequestMappingInfo(
                new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), useSuffixPatternMatch(), useTrailingSlashMatch(), getFileExtensions()),
                new RequestMethodsRequestCondition(annotation.method()),
                new ParamsRequestCondition(annotation.params()),
                new HeadersRequestCondition(annotation.headers()),
                new ConsumesRequestCondition(annotation.consumes(), annotation.headers()),
                new ProducesRequestCondition(annotation.produces(), annotation.headers(), getContentNegotiationManager()),
                customCondition);
    }

    public Map<String, String> getPrefixForPackage() {
        return prefixForPackage;
    }

    public void setPrefixForPackage(Map<String, String> prefixForPackage) {
        this.prefixForPackage = prefixForPackage;
    }
}