package summer.aop;

import java.util.List;

/**
 * @author li
 * @version 1 (2015年10月12日 下午4:24:42)
 * @since Java7
 */
public class PolymerAopFilter implements AopFilter {
    private List<AopFilter> aopFilters;

    public PolymerAopFilter(List<AopFilter> aopFilters) {
        this.aopFilters = aopFilters;
    }

    public void doFilter(AopChain chain) {
        for (AopFilter aopFilter : aopFilters) {
            aopFilter.doFilter(chain);
        }
    }
}