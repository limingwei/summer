package summer.aop.filter;

import summer.aop.AopChain;
import summer.aop.AopFilter;
import summer.basic.order.Ordered;

/**
 * @author li
 * @version 1 (2015年10月14日 下午9:28:26)
 * @since Java7
 */
public class AopFilterDelegate implements AopFilter, Ordered {
    private int order = 0;

    private AopFilter aopFilter;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public AopFilter getAopFilter() {
        return aopFilter;
    }

    public void setAopFilter(AopFilter aopFilter) {
        this.aopFilter = aopFilter;
    }

    public AopFilterDelegate() {}

    public AopFilterDelegate(AopFilter aopFilter) {
        setAopFilter(aopFilter);
    }

    public AopFilterDelegate(AopFilter aopFilter, int order) {
        setAopFilter(aopFilter);
        setOrder(order);
    }

    public void doFilter(AopChain chain) {
        getAopFilter().doFilter(chain);
    }
}