package cn.limw.summer.spring.web.servlet.mvc.handler.mapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.springframework.beans.factory.BeanFactoryUtils;
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

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年5月11日 上午10:46:14)
 * @since Java7
 * @see org.springframework.scheduling.config.AnnotationDrivenBeanDefinitionParser
 */
public class FixableRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    public static final String SCOPED_TARGET_NAME_PREFIX = "scopedTarget.";

    private static final Logger log = Logs.slf4j();

    private Map<String, String> prefixForPackage = new HashMap<String, String>();

    private boolean detectHandlerMethodsInAncestorContexts = false;

    public void setDetectHandlerMethodsInAncestorContexts(boolean detectHandlerMethodsInAncestorContexts) {
        this.detectHandlerMethodsInAncestorContexts = detectHandlerMethodsInAncestorContexts;
        super.setDetectHandlerMethodsInAncestorContexts(detectHandlerMethodsInAncestorContexts);
    }

    public Map<String, String> getPrefixForPackage() {
        return prefixForPackage;
    }

    public void setPrefixForPackage(Map<String, String> prefixForPackage) {
        this.prefixForPackage = prefixForPackage;
    }

    /**
     * initHandlerMethods
     */
    protected void initHandlerMethods() {
        if (logger.isDebugEnabled()) {
            logger.debug("Looking for request mappings in application context: " + getApplicationContext());
        }
        String[] beanNames = (detectHandlerMethodsInAncestorContexts // 
        ? BeanFactoryUtils.beanNamesForTypeIncludingAncestors(getApplicationContext(), Object.class) // 
                : getApplicationContext().getBeanNamesForType(Object.class));

        for (String beanName : beanNames) {
            if (!beanName.startsWith(SCOPED_TARGET_NAME_PREFIX) && isHandler(getApplicationContext().getType(beanName))) {
                Class<?> handlerType = getApplicationContext().getType(beanName);
                if (isFixableRequestMappingHandlerMapping(handlerType)) {
                    detectHandlerMethods(beanName);
                } else {
                    log.info("FixableRequestMappingHandlerMapping skiped beanName=" + beanName + ", handlerType=" + handlerType);
                }
            }
        }
        handlerMethodsInitialized(getHandlerMethods());
    }

    /**
     * isFixableRequestMappingHandlerMapping
     */
    private Boolean isFixableRequestMappingHandlerMapping(Class<?> handlerType) {
        String controllerType = handlerType.getName();
        for (Entry<String, String> entry : getPrefixForPackage().entrySet()) {
            if (controllerType.startsWith(entry.getKey())) {
                return true;
            }
        }
        return false;
    }

    /**
     * TODO 先把open的jar加进来, 然后这里, 判断是open的, 就加 /api/前缀
     */
    private String[] resolveEmbeddedValuesInPatterns(Method method, RequestMapping requestMapping) {
        String[] values = resolveEmbeddedValuesInPatterns(requestMapping.value());
        String controllerType = method.getDeclaringClass().getName();
        if (null != getPrefixForPackage() && !getPrefixForPackage().isEmpty()) {
            for (Entry<String, String> entry : getPrefixForPackage().entrySet()) {
                if (controllerType.startsWith(entry.getKey())) {
                    for (int i = 0; i < values.length; i++) {
                        String value = values[i];
                        values[i] = entry.getValue() + value;
                    }
                }
            }
        }
        return values;
    }

    /**
     * 添加时候排重
     */
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        HandlerMethod oldHandlerMethod = getHandlerMethods().get(mapping);
        if (null == oldHandlerMethod) {
            log.info("mapping repeat oldHandlerMethod=" + oldHandlerMethod + ", mapping=" + mapping);
            super.registerHandlerMethod(handler, method, mapping);
        }
    }

    protected RequestMappingInfo createRequestMappingInfo(Method method, RequestMapping annotation, RequestCondition<?> customCondition) {
        String[] patterns = resolveEmbeddedValuesInPatterns(method, annotation);
        return new RequestMappingInfo(
                new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), useSuffixPatternMatch(), useTrailingSlashMatch(), getFileExtensions()),
                new RequestMethodsRequestCondition(annotation.method()),
                new ParamsRequestCondition(annotation.params()),
                new HeadersRequestCondition(annotation.headers()),
                new ConsumesRequestCondition(annotation.consumes(), annotation.headers()),
                new ProducesRequestCondition(annotation.produces(), annotation.headers(), getContentNegotiationManager()),
                customCondition);
    }

    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = null;
        RequestMapping methodAnnotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        if (methodAnnotation != null) {
            RequestCondition<?> methodCondition = getCustomMethodCondition(method);
            info = createRequestMappingInfo(method, methodAnnotation, methodCondition);
            RequestMapping typeAnnotation = AnnotationUtils.findAnnotation(handlerType, RequestMapping.class);
            if (typeAnnotation != null) {
                RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
                info = createRequestMappingInfo(typeAnnotation, typeCondition).combine(info);
            }
        }
        return info;
    }
}