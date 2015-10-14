package summer.mvc.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import summer.aop.AopChain;
import summer.aop.AopFilter;
import summer.log.Logger;
import summer.mvc.Mvc;
import summer.mvc.ViewProcessor;
import summer.mvc.annotation.At;
import summer.util.Log;

/**
 * @author li
 * @version 1 (2015年10月12日 下午6:53:55)
 * @since Java7
 */
public class ViewProcessorAopFilter implements AopFilter {
    private static final Logger log = Log.slf4j();

    private ViewProcessor viewProcessor;

    public ViewProcessorAopFilter(ViewProcessor viewProcessor) {
        this.viewProcessor = viewProcessor;
    }

    public void doFilter(AopChain chain) {
        if (null != chain.getMethod().getAnnotation(At.class)) {
            Object result = chain.doFilter().getResult();

            HttpServletRequest request = Mvc.getRequest();
            HttpServletResponse response = Mvc.getResponse();
            viewProcessor.process(request, response, result);
            log.info("ViewProcessor 处理视图, result=" + result + ", method=" + chain.getMethod() + ", chain=" + chain);
        } else {
            chain.doFilter();
        }
    }
}