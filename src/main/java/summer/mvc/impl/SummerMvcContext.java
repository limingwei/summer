package summer.mvc.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import summer.ioc.BeanDefinition;
import summer.ioc.IocContext;
import summer.log.Logger;
import summer.mvc.ActionHandler;
import summer.mvc.MvcContext;
import summer.mvc.annotation.At;
import summer.util.Log;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月12日 下午3:34:29)
 * @since Java7
 */
public class SummerMvcContext implements MvcContext {
    private static final Logger log = Log.slf4j();

    private Map<String, ActionHandler> actionHandlerMapping;

    private IocContext iocContext;

    public IocContext getIocContext() {
        return iocContext;
    }

    public SummerMvcContext(IocContext iocContext) {
        this.iocContext = iocContext;
        init();
    }

    public ActionHandler getActionHandler(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String method = request.getMethod();
        log.info("servletPath={}, method={}", servletPath, method);

        String key = servletPath + "@" + method;
        return actionHandlerMapping.get(key);
    }

    private void init() {
        actionHandlerMapping = new HashMap<String, ActionHandler>();

        List<BeanDefinition> beanDefinitions = getIocContext().getBeanDefinitions();

        for (BeanDefinition beanDefinition : beanDefinitions) {
            List<Method> methods = Reflect.getPublicMethods(beanDefinition.getBeanType());
            for (Method method : methods) {
                At atAnnotation = method.getAnnotation(At.class);
                if (null != atAnnotation) {
                    String[] atAnnotationValues = atAnnotation.value();
                    String[] atAnnotationMethods = atAnnotation.method();

                    for (String atAnnotationValue : atAnnotationValues) {
                        for (String atAnnotationMethod : atAnnotationMethods) {
                            String key = atAnnotationValue + "@" + atAnnotationMethod;
                            actionHandlerMapping.put(key, new MethodActionHandler(method));

                            log.info("添加 @At(value=\"" + atAnnotationValue + "\", method=\"" + atAnnotationMethod + "\"), method={}", atAnnotation, method);
                        }
                    }
                }
            }
        }
    }
}