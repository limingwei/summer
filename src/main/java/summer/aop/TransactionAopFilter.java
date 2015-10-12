package summer.aop;

import summer.log.Logger;
import summer.util.Log;

/**
 * @author li
 * @version 1 (2015年10月12日 下午8:03:47)
 * @since Java7
 */
public class TransactionAopFilter implements AopFilter {
    private static final Logger log = Log.slf4j();

    public void doFilter(AopChain chain) {
        long start = System.nanoTime();
        chain.doFilter();
        log.info(this + ", TransactionAopFilter, 耗时 {} nano seconds, {}", (System.nanoTime() - start), chain);
    }
}