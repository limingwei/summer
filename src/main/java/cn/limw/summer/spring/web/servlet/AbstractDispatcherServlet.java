package cn.limw.summer.spring.web.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.OrderComparator;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.ViewResolver;

/**
 * @author li
 * @version 1 (2015年5月13日 上午11:51:17)
 * @since Java7
 */
public class AbstractDispatcherServlet extends FieldAccessDispatcherServlet {
    private static final long serialVersionUID = -7526102474403201093L;

    protected void initStrategies(ApplicationContext context) {
        doInitMultipartResolver(context);
        doInitLocaleResolver(context);
        doInitThemeResolver(context);
        doInitHandlerMappings(context);
        doInitHandlerAdapters(context);
        doInitHandlerExceptionResolvers(context);
        doInitRequestToViewNameTranslator(context);
        doInitViewResolvers(context);
        doInitFlashMapManager(context);
    }

    public void doInitHandlerAdapters(ApplicationContext context) {
        setHandlerAdapters(null);

        if (getDetectAllHandlerAdapters()) {
            // Find all HandlerAdapters in the ApplicationContext, including ancestor contexts.
            Map<String, HandlerAdapter> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerAdapter.class, true, false);
            if (!matchingBeans.isEmpty()) {
                List<HandlerAdapter> handlerAdapters = new ArrayList<HandlerAdapter>(matchingBeans.values());
                // We keep HandlerAdapters in sorted order.
                OrderComparator.sort(handlerAdapters);
                setHandlerAdapters(handlerAdapters);
            }
        }
        else {
            try {
                HandlerAdapter ha = context.getBean(HANDLER_ADAPTER_BEAN_NAME, HandlerAdapter.class);
                setHandlerAdapters(Collections.singletonList(ha));
            } catch (NoSuchBeanDefinitionException ex) {
                // Ignore, we'll add a default HandlerAdapter later.
            }
        }

        // Ensure we have at least some HandlerAdapters, by registering
        // default HandlerAdapters if no other adapters are found.
        if (getHandlerAdapters() == null) {
            setHandlerAdapters(getDefaultStrategies(context, HandlerAdapter.class));
            if (logger.isDebugEnabled()) {
                logger.debug("No HandlerAdapters found in servlet '" + getServletName() + "': using default");
            }
        }
    }

    public void doInitHandlerExceptionResolvers(ApplicationContext context) {
        setHandlerExceptionResolvers(null);

        if (getDetectAllHandlerExceptionResolvers()) {
            // Find all HandlerExceptionResolvers in the ApplicationContext, including ancestor contexts.
            Map<String, HandlerExceptionResolver> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerExceptionResolver.class, true, false);
            if (!matchingBeans.isEmpty()) {
                List<HandlerExceptionResolver> handlerExceptionResolvers = new ArrayList<HandlerExceptionResolver>(matchingBeans.values());
                // We keep HandlerExceptionResolvers in sorted order.
                OrderComparator.sort(handlerExceptionResolvers);
                setHandlerExceptionResolvers(handlerExceptionResolvers);
            }
        }
        else {
            try {
                HandlerExceptionResolver her = context.getBean(HANDLER_EXCEPTION_RESOLVER_BEAN_NAME, HandlerExceptionResolver.class);
                setHandlerExceptionResolvers(Collections.singletonList(her));
            } catch (NoSuchBeanDefinitionException ex) {
                // Ignore, no HandlerExceptionResolver is fine too.
            }
        }

        // Ensure we have at least some HandlerExceptionResolvers, by registering
        // default HandlerExceptionResolvers if no other resolvers are found.
        if (getHandlerExceptionResolvers() == null) {
            setHandlerExceptionResolvers(getDefaultStrategies(context, HandlerExceptionResolver.class));
            if (logger.isDebugEnabled()) {
                logger.debug("No HandlerExceptionResolvers found in servlet '" + getServletName() + "': using default");
            }
        }
    }

    public void doInitRequestToViewNameTranslator(ApplicationContext context) {
        try {
            setViewNameTranslator(context.getBean(REQUEST_TO_VIEW_NAME_TRANSLATOR_BEAN_NAME, RequestToViewNameTranslator.class));
            if (logger.isDebugEnabled()) {
                logger.debug("Using RequestToViewNameTranslator [" + getViewNameTranslator() + "]");
            }
        } catch (NoSuchBeanDefinitionException ex) {
            // We need to use the default.
            setViewNameTranslator(getDefaultStrategy(context, RequestToViewNameTranslator.class));
            if (logger.isDebugEnabled()) {
                logger.debug("Unable to locate RequestToViewNameTranslator with name '" + REQUEST_TO_VIEW_NAME_TRANSLATOR_BEAN_NAME + "': using default [" + getViewNameTranslator() + "]");
            }
        }
    }

    public void doInitHandlerMappings(ApplicationContext context) {
        setHandlerMappings(null);

        if (getDetectAllHandlerMappings()) {
            // Find all HandlerMappings in the ApplicationContext, including ancestor contexts.
            Map<String, HandlerMapping> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerMapping.class, true, false);
            if (!matchingBeans.isEmpty()) {
                List<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>(matchingBeans.values());
                OrderComparator.sort(handlerMappings);
                setHandlerMappings(handlerMappings);
            }
        }
        else {
            try {
                HandlerMapping hm = context.getBean(HANDLER_MAPPING_BEAN_NAME, HandlerMapping.class);
                setHandlerMappings(Collections.singletonList(hm));
            } catch (NoSuchBeanDefinitionException ex) {
                // Ignore, we'll add a default HandlerMapping later.
            }
        }

        // Ensure we have at least one HandlerMapping, by registering
        // a default HandlerMapping if no other mappings are found.
        if (getHandlerMappings() == null) {
            setHandlerMappings(getDefaultStrategies(context, HandlerMapping.class));
            if (logger.isDebugEnabled()) {
                logger.debug("No HandlerMappings found in servlet '" + getServletName() + "': using default");
            }
        }
    }

    public void doInitFlashMapManager(ApplicationContext context) {
        try {
            setFlashMapManager(context.getBean(FLASH_MAP_MANAGER_BEAN_NAME, FlashMapManager.class));
            if (logger.isDebugEnabled()) {
                logger.debug("Using FlashMapManager [" + getFlashMapManager() + "]");
            }
        } catch (NoSuchBeanDefinitionException ex) {
            setFlashMapManager(getDefaultStrategy(context, FlashMapManager.class));
            if (logger.isDebugEnabled()) {
                logger.debug("Unable to locate FlashMapManager with name '" + FLASH_MAP_MANAGER_BEAN_NAME + "': using default [" + getFlashMapManager() + "]");
            }
        }
    }

    public void doInitViewResolvers(ApplicationContext context) {
        setViewResolvers(null);

        if (getDetectAllViewResolvers()) {
            // Find all ViewResolvers in the ApplicationContext, including ancestor contexts.
            Map<String, ViewResolver> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, ViewResolver.class, true, false);
            if (!matchingBeans.isEmpty()) {
                List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>(matchingBeans.values());
                // We keep ViewResolvers in sorted order.
                OrderComparator.sort(viewResolvers);
                setViewResolvers(viewResolvers);
            }
        }
        else {
            try {
                ViewResolver vr = context.getBean(VIEW_RESOLVER_BEAN_NAME, ViewResolver.class);
                setViewResolvers(Collections.singletonList(vr));
            } catch (NoSuchBeanDefinitionException ex) {
                // Ignore, we'll add a default ViewResolver later.
            }
        }

        if (getViewResolvers() == null) {
            setViewResolvers(getDefaultStrategies(context, ViewResolver.class));
            if (logger.isDebugEnabled()) {
                logger.debug("No ViewResolvers found in servlet '" + getServletName() + "': using default");
            }
        }
    }

    public void doInitLocaleResolver(ApplicationContext context) {
        try {
            setLocaleResolver(context.getBean(LOCALE_RESOLVER_BEAN_NAME, LocaleResolver.class));
            if (logger.isDebugEnabled()) {
                logger.debug("Using LocaleResolver [" + getLocaleResolver() + "]");
            }
        } catch (NoSuchBeanDefinitionException ex) {
            setLocaleResolver(getDefaultStrategy(context, LocaleResolver.class));
            if (logger.isDebugEnabled()) {
                logger.debug("Unable to locate LocaleResolver with name '" + LOCALE_RESOLVER_BEAN_NAME + "': using default [" + getLocaleResolver() + "]");
            }
        }
    }

    public void doInitThemeResolver(ApplicationContext context) {
        try {
            setThemeResolver(context.getBean(THEME_RESOLVER_BEAN_NAME, ThemeResolver.class));
            if (logger.isDebugEnabled()) {
                logger.debug("Using ThemeResolver [" + getThemeResolver() + "]");
            }
        } catch (NoSuchBeanDefinitionException ex) {
            setThemeResolver(getDefaultStrategy(context, ThemeResolver.class));
            if (logger.isDebugEnabled()) {
                logger.debug("Unable to locate ThemeResolver with name '" + THEME_RESOLVER_BEAN_NAME + "': using default [" + getThemeResolver() + "]");
            }
        }
    }

    public void doInitMultipartResolver(ApplicationContext context) {
        try {
            setMultipartResolver(context.getBean(MULTIPART_RESOLVER_BEAN_NAME, MultipartResolver.class));
            if (logger.isDebugEnabled()) {
                logger.debug("Using MultipartResolver [" + getMultipartResolver() + "]");
            }
        } catch (NoSuchBeanDefinitionException ex) {
            setMultipartResolver(null);
            if (logger.isDebugEnabled()) {
                logger.debug("Unable to locate MultipartResolver with name '" + MULTIPART_RESOLVER_BEAN_NAME + "': no multipart request handling provided");
            }
        }
    }
}