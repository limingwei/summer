package summer.ioc.loader;

import java.util.ArrayList;
import java.util.List;

import summer.ioc.BeanDefinition;
import summer.ioc.IocLoader;
import summer.log.Logger;
import summer.util.Log;

/**
 * @author li
 * @version 1 (2015年10月10日 上午10:37:06)
 * @since Java7
 */
public class AnnotationIocLoader implements IocLoader {
    private static final Logger log = Log.slf4j();

    public List<BeanDefinition> getBeanDefinitions() {
        ArrayList<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
        log.info("getBeanDefinitions() returning {}", beanDefinitions.size());
        return beanDefinitions;
    }
}