package summer.ioc.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import summer.converter.ConvertService;
import summer.converter.impl.SummerConvertService;
import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.ioc.IocContext;
import summer.ioc.IocLoader;
import summer.log.Logger;
import summer.util.Assert;
import summer.util.Log;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月10日 上午10:33:37)
 * @since Java7
 */
public class SummerIocContext implements IocContext {
    private static final Logger log = Log.slf4j();

    private List<BeanDefinition> beanDefinitions;

    private Map<BeanDefinition, Object> beanInstances;

    private IocLoader iocLoader;

    private ConvertService convertService;

    public IocLoader getIocLoader() {
        return iocLoader;
    }

    public SummerIocContext(IocLoader iocLoader) {
        this.iocLoader = iocLoader;

        this.beanDefinitions = new ArrayList<BeanDefinition>();
        this.beanDefinitions.addAll(iocLoader.getBeanDefinitions());

        this.convertService = new SummerConvertService();

        this.beanInstances = new HashMap<BeanDefinition, Object>();

        log.info("SummerIocContext init, iocLoader={}, beanDefinitions={}, convertService={}", iocLoader, beanDefinitions, convertService);
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
            Class<?> beanType = beanDefinition.getBeanType();
            Object beanInstance = Reflect.newInstance(beanType);

            for (BeanField beanField : beanDefinition.getBeanFields()) {
                if (BeanField.TYPE_PROPERTY_VALUE.equals(beanField.getType())) {
                    Field field = Reflect.getField(beanType, beanField.getName());
                    Object value = convertService.convert(String.class, field.getType(), beanField.getValue());
                    Reflect.setFieldValue(beanInstance, field, value);
                } else if (BeanField.TYPE_REFERENCE.equals(beanField.getType())) {

                }
            }

            beanInstances.put(beanDefinition, beanInstance);
            instance = beanInstance;
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