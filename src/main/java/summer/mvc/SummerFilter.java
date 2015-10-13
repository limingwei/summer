package summer.mvc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import summer.ioc.IocLoader;
import summer.ioc.impl.SummerIocContext;
import summer.ioc.loader.AnnotationIocLoader;
import summer.ioc.loader.ComplexIocLoader;
import summer.ioc.loader.XmlIocLoader;
import summer.log.Logger;
import summer.util.Log;
import summer.util.Stream;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:48:21)
 * @since Java7
 */
public class SummerFilter implements Filter {
    private static final Logger log = Log.slf4j();

    public static final String SUMMER_CONFIG_INIT_PARAMETER_NAME = "summerConfig";

    private SummerDispatcher summerDispatcher;

    public void init(FilterConfig filterConfig) throws ServletException {
        String summerConfig = filterConfig.getInitParameter(SUMMER_CONFIG_INIT_PARAMETER_NAME);
        if (null == summerConfig || summerConfig.isEmpty()) {
            summerConfig = "summer.xml";
        }
        String configFilePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + summerConfig;
        log.info("init() configFilePath=" + configFilePath);

        XmlIocLoader xmlIocLoader = new XmlIocLoader(Stream.newFileInputStream(configFilePath));
        AnnotationIocLoader annotationIocLoader = new AnnotationIocLoader(new String[] {});
        ComplexIocLoader iocLoader = new ComplexIocLoader(new IocLoader[] { annotationIocLoader, xmlIocLoader });
        summerDispatcher = new SummerDispatcher(new SummerIocContext(iocLoader));
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (summerDispatcher.doDispatch((HttpServletRequest) request, (HttpServletResponse) response)) {
            //
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        log.info("destroy()");
    }
}