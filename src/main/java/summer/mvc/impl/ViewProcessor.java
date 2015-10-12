package summer.mvc.impl;

import summer.aop.AopChain;
import summer.aop.AopFilter;
import summer.log.Logger;
import summer.mvc.annotation.At;
import summer.util.Log;

/**
 * @author li
 * @version 1 (2015年10月12日 下午4:11:20)
 * @since Java7
 */
public class ViewProcessor implements AopFilter {
    private static final Logger log = Log.slf4j();

    public void doFilter(AopChain chain) {
        if (null != chain.getMethod().getAnnotation(At.class)) {
            Object result = chain.doFilter().getResult();
            log.info("ViewProcessor 处理视图, result=" + result + ", method=" + chain.getMethod() + ", chain=" + chain);
        } else {
            chain.doFilter();
        }
    }
}