package summer.aop.util;

/**
 * @author li
 * @version 1 (2015年10月11日 上午9:27:02)
 * @since Java7
 */

public interface MethodInvoker {
    public Object invoke(Object target, Object[] args);
}