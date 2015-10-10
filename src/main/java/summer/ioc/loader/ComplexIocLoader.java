package summer.ioc.loader;

import java.util.ArrayList;
import java.util.List;

import summer.ioc.BeanDefinition;
import summer.ioc.IocLoader;

/**
 * @author li
 * @version 1 (2015年10月10日 上午10:35:52)
 * @since Java7
 */
public class ComplexIocLoader implements IocLoader {
    private ArrayList<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();

    public ComplexIocLoader(IocLoader[] iocLoaders) {
        for (IocLoader iocLoader : iocLoaders) {
            beanDefinitions.addAll(iocLoader.getBeanDefinitions());
        }
    }

    public List<BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }
}