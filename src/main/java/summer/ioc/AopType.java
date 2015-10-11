package summer.ioc;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author li
 * @version 1 (2015年10月11日 上午11:57:29)
 * @since Java7
 */
public interface AopType {
    public Map<String, Method> $getMethods();

    public void $setMethods(Map<String, Method> methods);
}