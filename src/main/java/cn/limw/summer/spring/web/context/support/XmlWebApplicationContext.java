package cn.limw.summer.spring.web.context.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.limw.summer.spring.web.servlet.mvc.handler.mapping.FixableRequestMappingHandlerMapping;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年5月14日 上午8:59:44)
 * @since Java7
 */
public class XmlWebApplicationContext extends AbstractXmlWebApplicationContext {
    private static final Logger log = Logs.slf4j();

    private FixableRequestMappingHandlerMapping fixableRequestMappingHandlerMapping;

    public synchronized FixableRequestMappingHandlerMapping getFixableRequestMappingHandlerMapping() {
        if (null == fixableRequestMappingHandlerMapping) {
            try {
                fixableRequestMappingHandlerMapping = getBean(FixableRequestMappingHandlerMapping.class);
            } catch (NoSuchBeanDefinitionException e) {
                //
            }
        }
        return fixableRequestMappingHandlerMapping;
    }

    /**
     * @see cn.limw.summer.spring.web.servlet.mvc.handler.mapping.FixableRequestMappingHandlerMapping#initHandlerMethods()
     */
    public String[] getBeanNamesForType(Class<?> type) {
        if (type.equals(Object.class)) {
            if (null != getFixableRequestMappingHandlerMapping()) {
                StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
                StackTraceElement stackTraceElement_2 = stackTraceElements[2];
                if ("org.springframework.web.servlet.handler.AbstractHandlerMethodMapping".equals(stackTraceElement_2.getClassName()) && "initHandlerMethods".equals(stackTraceElement_2.getMethodName())) {
                    StackTraceElement stackTraceElement_4 = stackTraceElements[4];
                    if ("org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping".equals(stackTraceElement_4.getClassName()) && "afterPropertiesSet".equals(stackTraceElement_4.getMethodName())) {
                        return getBeanNamesForRequestMappingHandlerMapping();
                    }
                }
            }
        }

        return super.getBeanNamesForType(type);
    }

    private String[] getBeanNamesForRequestMappingHandlerMapping() {
        List<String> result = new ArrayList<String>();
        String[] beanNames = getBeanNamesForType(Object.class);
        for (String beanName : beanNames) {
            if (!beanName.startsWith(FixableRequestMappingHandlerMapping.SCOPED_TARGET_NAME_PREFIX) && isHandler(getType(beanName))) {
                Class<?> handlerType = getType(beanName);
                if (isFixableRequestMappingHandlerMapping(handlerType)) {
                    // 不返回
                    log.info("getBeanNamesForRequestMappingHandlerMapping() 不返回 " + handlerType + " " + beanName);
                } else {
                    result.add(beanName); // 要返回
                }
            }
        }
        return result.toArray(new String[result.size()]);
    }

    protected boolean isHandler(Class<?> beanType) {
        return ((AnnotationUtils.findAnnotation(beanType, Controller.class) != null) || (AnnotationUtils.findAnnotation(beanType, RequestMapping.class) != null));
    }

    private boolean isFixableRequestMappingHandlerMapping(Class<?> handlerType) {
        String controllerType = handlerType.getName();
        for (Entry<String, String> entry : getFixableRequestMappingHandlerMapping().getPrefixForPackage().entrySet()) {
            if (controllerType.startsWith(entry.getKey())) {
                return true;
            }
        }
        return false;
    }
}