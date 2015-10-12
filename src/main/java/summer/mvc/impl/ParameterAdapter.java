package summer.mvc.impl;

import summer.aop.AopChain;
import summer.aop.AopFilter;
import summer.log.Logger;
import summer.mvc.annotation.At;
import summer.util.Log;

/**
 * @author li
 * @version 1 (2015年10月12日 下午4:05:01)
 * @since Java7
 */
public class ParameterAdapter implements AopFilter {
    private static final Logger log = Log.slf4j();

    public void doFilter(AopChain chain) {
        if (null != chain.getMethod().getAnnotation(At.class)) {
            log.info("ParameterAdapter 适配参数, chain=" + chain);
            chain.doFilter();
        } else {
            chain.doFilter();
        }
    }
}