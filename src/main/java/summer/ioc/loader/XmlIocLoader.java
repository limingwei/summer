package summer.ioc.loader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import summer.ioc.BeanDefinition;
import summer.ioc.IocLoader;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月10日 上午10:37:17)
 * @since Java7
 */
public class XmlIocLoader implements IocLoader {
    private ArrayList<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();

    public XmlIocLoader(InputStream inputStream) {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanType(Reflect.classForName("summer.demo.action.IndexAction"));
        beanDefinitions.add(beanDefinition);
    }

    public List<BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }
}