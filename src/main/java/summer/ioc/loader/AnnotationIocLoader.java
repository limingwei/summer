package summer.ioc.loader;

import java.util.ArrayList;
import java.util.List;

import summer.ioc.BeanDefinition;
import summer.ioc.IocLoader;

/**
 * @author li
 * @version 1 (2015年10月10日 上午10:37:06)
 * @since Java7
 */
public class AnnotationIocLoader implements IocLoader {
    public List<BeanDefinition> getBeanDefinitions() {
        return new ArrayList<BeanDefinition>();
    }
}