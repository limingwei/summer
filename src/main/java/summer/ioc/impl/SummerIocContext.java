package summer.ioc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import summer.ioc.BeanDefinition;
import summer.ioc.IocContext;
import summer.ioc.IocLoader;
import summer.util.Assert;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月10日 上午10:33:37)
 * @since Java7
 */
public class SummerIocContext implements IocContext {
    private List<BeanDefinition> beanDefinitions;

    private Map<BeanDefinition, Object> beanInstances;

    private IocLoader iocLoader;

    public IocLoader getIocLoader() {
        return iocLoader;
    }

    public SummerIocContext(IocLoader iocLoader) {
        this.iocLoader = iocLoader;

        this.beanDefinitions = new ArrayList<BeanDefinition>();
        this.beanDefinitions.addAll(iocLoader.getBeanDefinitions());

        this.beanInstances = new HashMap<BeanDefinition, Object>();
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        BeanDefinition beanDefinition = findMatchBeanDefinition(type);
        Assert.noNull(beanDefinition, "not found BeanDefinition for type " + type + ", beanDefinitions=" + beanDefinitions);

        return (T) getBeanInstance(beanDefinition);
    }

    public synchronized Object getBeanInstance(BeanDefinition beanDefinition) {
        Object instance = beanInstances.get(beanDefinition);
        if (null == instance) {
            beanInstances.put(beanDefinition, instance = Reflect.newInstance(beanDefinition.getBeanType()));
        }
        return instance;
    }

    public BeanDefinition findMatchBeanDefinition(Class<?> type) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (type.isAssignableFrom(beanDefinition.getBeanType())) {
                return beanDefinition;
            }
        }
        return null;
    }

    public <T> T getBean(Class<T> type, String name) {
        return null;
    }

    public Object getBean(String name) {
        return null;
    }
}