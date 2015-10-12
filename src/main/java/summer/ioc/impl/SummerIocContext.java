package summer.ioc.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
import summer.ioc.ReferenceType;
import summer.ioc.SummerCompiler;
import summer.ioc.compiler.CachedSummerCompiler;
import summer.ioc.compiler.JavassistSummerCompiler;
import summer.log.Logger;
import summer.util.Assert;
import summer.util.Log;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月10日 上午10:33:37)
 * @since Java7
 */
@SuppressWarnings("unchecked")
public class SummerIocContext extends AbstractSummerIocContext {
    private static final Logger log = Log.slf4j();

    private List<BeanDefinition> beanDefinitions;

    private Map<BeanDefinition, Object> beanInstances;

    private IocLoader iocLoader;

    private ConvertService convertService;

    private SummerCompiler summerCompiler;

    public IocLoader getIocLoader() {
        return iocLoader;
    }

    public SummerIocContext(IocLoader iocLoader) {
        this.iocLoader = iocLoader;

        this.beanDefinitions = new ArrayList<BeanDefinition>();
        this.beanDefinitions.addAll(iocLoader.getBeanDefinitions());

        this.convertService = new SummerConvertService();

        this.beanInstances = new HashMap<BeanDefinition, Object>();

        this.summerCompiler = new CachedSummerCompiler(new JavassistSummerCompiler());

        log.info("SummerIocContext init, iocLoader={}, beanDefinitions={}, convertService={}", iocLoader, beanDefinitions, convertService);
    }
    
    public List<BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    public <T> T doGetBean(Class<T> type) {
        BeanDefinition beanDefinition = findMatchBeanDefinition(type);
        Assert.noNull(beanDefinition, "not found BeanDefinition for type " + type + ", beanDefinitions=" + beanDefinitions);

        return (T) getBeanInstance(beanDefinition);
    }

    public Object doGetBean(String id) {
        BeanDefinition beanDefinition = findMatchBeanDefinition(id);
        Assert.noNull(beanDefinition, "not found BeanDefinition for id " + id + ", beanDefinitions=" + beanDefinitions);

        return getBeanInstance(beanDefinition);
    }

    public <T> T doGetBean(Class<T> type, String id) {
        if (IocContext.class.equals(type)) {
            return (T) this;
        } else {
            BeanDefinition beanDefinition = findMatchBeanDefinition(type, id);
            Assert.noNull(beanDefinition, "not found BeanDefinition for type " + type + ", id=" + id + ", beanDefinitions=" + beanDefinitions);

            return (T) getBeanInstance(beanDefinition);
        }
    }

    private synchronized Object getBeanInstance(BeanDefinition beanDefinition) {
        Object instance = beanInstances.get(beanDefinition);
        if (null == instance) { // 延迟初始化
            Class<?> beanType = beanDefinition.getBeanType();

            // TODO 先生成代理, 代理加上Aop, 然后实例化
            Object beanInstance = newBeanInstance(beanType);

            for (BeanField beanField : beanDefinition.getBeanFields()) {
                if (BeanField.INJECT_TYPE_VALUE.equals(beanField.getInjectType())) {
                    Field field = Reflect.getField(beanType, beanField.getName());
                    Object value = convertService.convert(String.class, field.getType(), beanField.getValue());
                    Reflect.setFieldValue(beanInstance, field, value);
                } else if (BeanField.INJECT_TYPE_REFERENCE.equals(beanField.getInjectType())) {
                    Field field = Reflect.getField(beanType, beanField.getName());
                    ReferenceType value = (ReferenceType) newReferenceInstance(beanDefinition, beanField);
                    value.setIocContext(this);
                    Reflect.setFieldValue(beanInstance, field, value);
                }
            }

            beanInstances.put(beanDefinition, beanInstance);
            instance = beanInstance;
        }
        return instance;
    }

    private BeanDefinition findMatchBeanDefinition(Class<?> type, String id) {
        Assert.noEmpty(id, "id 不可以为空");

        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (type.isAssignableFrom(beanDefinition.getBeanType()) && id.equals(beanDefinition.getId())) {
                return beanDefinition;
            }
        }
        return null;
    }

    private BeanDefinition findMatchBeanDefinition(Class<?> type) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (type.isAssignableFrom(beanDefinition.getBeanType())) {
                return beanDefinition;
            }
        }
        return null;
    }

    private BeanDefinition findMatchBeanDefinition(String id) {
        Assert.noEmpty(id, "id 不可以为空");
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (id.equals(beanDefinition.getId())) {
                return beanDefinition;
            }
        }
        return null;
    }

    private Object newReferenceInstance(BeanDefinition beanDefinition, BeanField beanField) {
        Class<?> type = summerCompiler.compileReference(beanDefinition, beanField);
        return Reflect.newInstance(type);
    }

    private Object newBeanInstance(Class<?> beanType) {
        if (Modifier.isFinal(beanType.getModifiers())) {
            log.warn("type {} is final, can not aop", beanType);
            return Reflect.newInstance(beanType);
        } else {
            Class<?> type = summerCompiler.compileClass(beanType);
            return Reflect.newInstance(type);
        }
    }
}