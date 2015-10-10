package summer.mvc;

import java.io.IOException;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import summer.ioc.IocContext;
import summer.ioc.IocLoader;
import summer.ioc.impl.SummerIocContext;
import summer.ioc.loader.AnnotationIocLoader;
import summer.ioc.loader.ComplexIocLoader;
import summer.ioc.loader.XmlIocLoader;

/**
 * @author li
 * @version 1 (2015年10月10日 上午10:02:56)
 * @since Java7
 */
public class SummerServletContextListener implements ServletContextListener {
    private IocContext iocContext;

    public synchronized IocContext getIocContext() {
        if (null == iocContext) {
            iocContext = new SummerIocContext(new ComplexIocLoader(new IocLoader[] { new AnnotationIocLoader(), new XmlIocLoader() }));
        }
        return iocContext;
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        addHelloWorldForAllRequest(servletContextEvent);
    }

    public void addHelloWorldForAllRequest(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        Dynamic dynamic = servletContext.addFilter("HelloWorldSummerFilter", new SummerFilter() {
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                response.getWriter().write("hello world, summer, https://github.com/limingwei/summer");
            }
        });

        EnumSet<DispatcherType> dispatcherTypes = EnumSet.allOf(DispatcherType.class);
        dispatcherTypes.add(DispatcherType.REQUEST);
        dispatcherTypes.add(DispatcherType.FORWARD);
        dispatcherTypes.add(DispatcherType.INCLUDE);

        boolean isMatchAfter = true;
        String[] urlPatterns = { "/*" };
        dynamic.addMappingForUrlPatterns(dispatcherTypes, isMatchAfter, urlPatterns);
    }

    public void contextDestroyed(ServletContextEvent sce) {}
}