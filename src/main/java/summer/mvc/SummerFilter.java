package summer.mvc;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import summer.ioc.BeanDefinition;
import summer.ioc.IocContext;
import summer.log.Logger;
import summer.mvc.annotation.At;
import summer.util.Log;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:48:21)
 * @since Java7
 */
public class SummerFilter implements Filter {
    private static final Logger log = Log.slf4j();

    private IocContext iocContext;

    private Map<String, Method> actionHandlerMapping;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (null == actionHandlerMapping) {
            init(null);
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String servletPath = httpServletRequest.getServletPath();
        String method = httpServletRequest.getMethod();
        log.info("servletPath={}, method={}", servletPath, method);

        String key = servletPath + "@" + method;

        Method actionMethod = actionHandlerMapping.get(key);
        if (null != actionMethod) {
            Object actionBean = getIocContext().getBean(actionMethod.getDeclaringClass());
            Reflect.invokeMethod(actionBean, actionMethod, new Object[] {});
        } else {
            log.info("action not found, servletPath=" + servletPath + ", method=" + method + ", actionHandlerMapping=" + actionHandlerMapping);
            httpServletResponse.getWriter().append("action not found, servletPath=" + servletPath + ", method=" + method);
            httpServletResponse.setStatus(404);
        }
    }

    public IocContext getIocContext() {
        return iocContext;
    }

    public void setIocContext(IocContext iocContext) {
        this.iocContext = iocContext;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        actionHandlerMapping = new HashMap<String, Method>();

        List<BeanDefinition> beanDefinitions = getIocContext().getBeanDefinitions();

        for (BeanDefinition beanDefinition : beanDefinitions) {
            Method[] methods = beanDefinition.getBeanType().getMethods();
            for (Method method : methods) {
                At atAnnotation = method.getAnnotation(At.class);
                if (null != atAnnotation) {
                    String[] atAnnotationValues = atAnnotation.value();
                    String[] atAnnotationMethods = atAnnotation.method();

                    for (String atAnnotationValue : atAnnotationValues) {
                        for (String atAnnotationMethod : atAnnotationMethods) {
                            String key = atAnnotationValue + "@" + atAnnotationMethod;
                            actionHandlerMapping.put(key, method);

                            log.info("添加 @At={}, method={}", atAnnotation, method);
                        }
                    }
                }
            }
        }
    }

    public void destroy() {}
}