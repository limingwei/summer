package summer.aop;

/**
 * @author li
 * @version 1 (2015年10月11日 上午9:27:02)
 * @since Java7
 */

public interface AopInvoker {
    public Object invoke(Object target, Object[] args);
}