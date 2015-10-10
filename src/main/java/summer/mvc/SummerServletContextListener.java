package summer.mvc;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author li
 * @version 1 (2015年10月10日 上午10:02:56)
 * @since Java7
 */
public class SummerServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        String filterName = "SummerFilter";
        Dynamic dynamic = servletContext.addFilter(filterName, new SummerFilter());

        EnumSet<DispatcherType> dispatcherTypes = EnumSet.allOf(DispatcherType.class);
        dispatcherTypes.add(DispatcherType.REQUEST);
        dispatcherTypes.add(DispatcherType.FORWARD);
        dispatcherTypes.add(DispatcherType.INCLUDE);

        boolean isMatchAfter = true;
        String[] urlPatterns = { "*.do" };
        dynamic.addMappingForUrlPatterns(dispatcherTypes, isMatchAfter, urlPatterns);
    }

    public void contextDestroyed(ServletContextEvent sce) {}
}