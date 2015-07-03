package cn.limw.summer.spring.web.servlet;

import java.util.List;

import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.ViewResolver;

import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2015年5月13日 下午12:13:03)
 * @since Java7
 */
public class FieldAccessDispatcherServlet extends DispatcherServlet {
    public static final long serialVersionUID = 4212696364853178128L;

    public LocaleResolver getLocaleResolver() {
        return (LocaleResolver) Mirrors.getFieldValue(this, "localeResolver");
    }

    public void setLocaleResolver(LocaleResolver localeResolver) {
        Mirrors.setFieldValue(this, "localeResolver", localeResolver);
    }

    public void setMultipartResolver(MultipartResolver multipartResolver) {
        Mirrors.setFieldValue(this, "multipartResolver", multipartResolver);
    }

    public List<HandlerAdapter> getHandlerAdapters() {
        return (List<HandlerAdapter>) Mirrors.getFieldValue(this, "handlerAdapters");
    }

    public void setHandlerAdapters(List<HandlerAdapter> handlerAdapters) {
        Mirrors.setFieldValue(this, "handlerAdapters", handlerAdapters);
    }

    public Boolean getDetectAllHandlerAdapters() {
        return (Boolean) Mirrors.getFieldValue(this, "detectAllHandlerAdapters");
    }

    public FlashMapManager getFlashMapManager() {
        return (FlashMapManager) Mirrors.getFieldValue(this, "flashMapManager");
    }

    public void setFlashMapManager(FlashMapManager flashMapManager) {
        Mirrors.setFieldValue(this, "flashMapManager", flashMapManager);
    }

    public ThemeResolver getThemeResolver() {
        return (ThemeResolver) Mirrors.getFieldValue(this, "themeResolver");
    }

    public void setThemeResolver(ThemeResolver themeResolver) {
        Mirrors.setFieldValue(this, "themeResolver", themeResolver);
    }

    public List<ViewResolver> getViewResolvers() {
        return (List<ViewResolver>) Mirrors.getFieldValue(this, "viewResolvers");
    }

    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        Mirrors.setFieldValue(this, "viewResolvers", viewResolvers);
    }

    public Boolean getDetectAllViewResolvers() {
        return (Boolean) Mirrors.getFieldValue(this, "detectAllViewResolvers");
    }

    public void setHandlerExceptionResolvers(List<HandlerExceptionResolver> handlerExceptionResolvers) {
        Mirrors.setFieldValue(this, "handlerExceptionResolvers", handlerExceptionResolvers);
    }

    public List<HandlerExceptionResolver> getHandlerExceptionResolvers() {
        return (List<HandlerExceptionResolver>) Mirrors.getFieldValue(this, "handlerExceptionResolvers");
    }

    public Boolean getDetectAllHandlerExceptionResolvers() {
        return (Boolean) Mirrors.getFieldValue(this, "detectAllHandlerExceptionResolvers");
    }

    public List<HandlerMapping> getHandlerMappings() {
        return (List<HandlerMapping>) Mirrors.getFieldValue(this, "handlerMappings");
    }

    public Boolean getDetectAllHandlerMappings() {
        return (Boolean) Mirrors.getFieldValue(this, "detectAllHandlerMappings");
    }

    public RequestToViewNameTranslator getViewNameTranslator() {
        return (RequestToViewNameTranslator) Mirrors.getFieldValue(this, "viewNameTranslator");
    }

    public void setViewNameTranslator(RequestToViewNameTranslator viewNameTranslator) {
        Mirrors.setFieldValue(this, "viewNameTranslator", viewNameTranslator);
    }

    public void setHandlerMappings(List<HandlerMapping> handlerMappings) {
        Mirrors.setFieldValue(this, "handlerMappings", handlerMappings);
    }
}