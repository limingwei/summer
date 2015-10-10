package summer.ioc.impl;

import summer.ioc.IocContext;
import summer.ioc.IocLoader;

/**
 * @author li
 * @version 1 (2015年10月10日 上午10:33:37)
 * @since Java7
 */
public class SummerIocContext implements IocContext {
    public SummerIocContext(IocLoader iocLoader) {}

    public <T> T getBean(Class<T> type, String name) {
        return null;
    }

    public <T> T getBean(Class<T> type) {
        return null;
    }

    public Object getBean(String name) {
        return null;
    }
}