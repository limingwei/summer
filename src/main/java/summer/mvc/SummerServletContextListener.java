package summer.mvc;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import summer.log.Logger;
import summer.util.Log;

/**
 * @author li
 * @version 1 (2015年10月10日 上午10:02:56)
 * @since Java7
 */
public class SummerServletContextListener implements ServletContextListener {
    private static final Logger log = Log.slf4j();

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("contextInitialized, servletContextEvent=" + servletContextEvent);

        ServletContext servletContext = servletContextEvent.getServletContext();

        Dynamic dynamic = servletContext.addFilter("summerFilter", SummerFilter.class.getName());

        Map<String, String> initParameters = new HashMap<String, String>();
        String value = servletContextEvent.getServletContext().getInitParameter(SummerFilter.SUMMER_CONFIG_INIT_PARAMETER_NAME);
        if (null != value && !value.isEmpty()) {
            initParameters.put(SummerFilter.SUMMER_CONFIG_INIT_PARAMETER_NAME, value);
        }
        dynamic.setInitParameters(initParameters);

        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE);
        dynamic.addMappingForUrlPatterns(dispatcherTypes, true, new String[] { "/*" });
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("contextDestroyed, servletContextEvent=" + servletContextEvent);
    }
}