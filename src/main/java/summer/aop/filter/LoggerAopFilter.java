package summer.aop.filter;

import summer.aop.AopChain;
import summer.aop.AopFilter;
import summer.basic.order.Order;
import summer.log.Logger;
import summer.util.Log;

/**
 * @author li
 * @version 1 (2015年10月11日 下午2:34:06)
 * @since Java7
 */
@Order(Integer.MIN_VALUE + 2)
public class LoggerAopFilter implements AopFilter {
    private static final Logger log = Log.slf4j();

    public void doFilter(AopChain chain) {
        long start = System.nanoTime();
        chain.doFilter();
        log.info("耗时 {} nano seconds, {}", (System.nanoTime() - start), chain);
    }
}