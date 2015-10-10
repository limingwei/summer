package summer.aop;

/**
 * @author li
 * @version 1 (2015年10月10日 下午10:50:24)
 * @since Java7
 */
public interface AopFilter {
    /**
     * AopFilter的实现类需要实现的方法
     */
    public void doFilter(AopChain chain);
}