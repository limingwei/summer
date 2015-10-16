package summer.ioc.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import summer.aop.AopType;
import summer.aop.AopTypeMeta;
import summer.converter.ConvertService;
import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.ioc.IocContext;
import summer.ioc.IocLoader;
import summer.ioc.SummerCompiler;
import summer.ioc.compiler.CachedSummerCompiler;
import summer.ioc.compiler.JavassistSummerCompiler;
import summer.ioc.impl.util.SummerIocContextUtil;
import summer.ioc.loader.XmlIocLoader;
import summer.ioc.util.IocUtil;
import summer.log.Logger;
import summer.util.Assert;
import summer.util.Log;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月10日 上午10:33:37)
 * @since Java7
 */
@SuppressWarnings({ "unchecked" })
public class SummerIocContext extends AbstractSummerIocContext {
    private static final Logger log = Log.slf4j();

    private List<BeanDefinition> beanDefinitions;

    private Map<BeanDefinition, Object> beanInstances;

    private IocLoader iocLoader;

    private ConvertService convertService;

    private SummerCompiler summerCompiler;

    public SummerIocContext(IocLoader iocLoader) {
        log.info("\n\n\thttps://github.com/limingwei/summer\n");

        this.iocLoader = iocLoader;

        this.beanInstances = new HashMap<BeanDefinition, Object>();

        this.summerCompiler = new CachedSummerCompiler(new JavassistSummerCompiler());

        log.info("SummerIocContext inited, iocLoader={}", iocLoader);
    }

    public IocLoader getIocLoader() {
        return iocLoader;
    }

    public synchronized ConvertService getConvertService() {
        if (null == convertService) {
            convertService = IocUtil.getConvertService(this);
        }
        return convertService;
    }

    public synchronized List<BeanDefinition> getBeanDefinitions() {
        if (null == beanDefinitions) {
            List<BeanDefinition> allBeanDefinitions = getIocLoader().getBeanDefinitions();
            this.beanDefinitions = SummerIocContextUtil.mergeBeanDefinitions(allBeanDefinitions);
        }
        return beanDefinitions;
    }

    public <T> T doGetBean(Class<T> type, String id) {
        if (IocContext.class.equals(type)) {
            return (T) this;
        } else {
            BeanDefinition beanDefinition = SummerIocContextUtil.findMatchBeanDefinition(getBeanDefinitions(), type, id);
            Assert.noNull(beanDefinition, "not found BeanDefinition for type " + type + ", id=" + id + ", beanDefinitions=" + getBeanDefinitions());

            return (T) SummerIocContextUtil.unwrapFactoryBean(getBeanInstance(beanDefinition));
        }
    }

    private synchronized Object getBeanInstance(BeanDefinition beanDefinition) {
        Object instance = beanInstances.get(beanDefinition);
        if (null == instance) { // 延迟初始化
            Class<?> beanType = beanDefinition.getBeanType();

            // 生成代理, 代理加上Aop, 然后实例化
            Object beanInstance = newBeanInstance(beanType);

            for (BeanField beanField : beanDefinition.getBeanFields()) {
                setInjectFieldAopTypeValue(beanType, beanInstance, beanField);
            }

            if (beanInstance instanceof AopType) {
                setAopTypeBeanInstanceFieldValue(beanDefinition, beanInstance);
            }

            log.info("inited bean " + beanDefinition.getId() + ", " + beanDefinition.getBeanType() + ", beanInstance=" + beanInstance);

            beanInstances.put(beanDefinition, beanInstance);
            instance = beanInstance;
        }
        return instance;
    }

    private void setAopTypeBeanInstanceFieldValue(BeanDefinition beanDefinition, Object beanInstance) {
        AopTypeMeta aopTypeMeta = ((AopType) beanInstance).getAopTypeMeta();
        aopTypeMeta.setIocContext(this);
    }

    private void setInjectFieldAopTypeValue(Class<?> beanType, Object beanInstance, BeanField beanField) {
        String fieldName = beanField.getName();
        if (BeanField.INJECT_TYPE_REFERENCE.equals(beanField.getInjectType())) { // TODO 判断是否已经初始化, 如果已经初始化, 就直接将Bean注入
            Class<?> fieldType = XmlIocLoader.getFieldType(beanType, fieldName);
            Object value = newBeanInstance(fieldType);

            if (value instanceof AopType) {
                AopTypeMeta aopTypeMeta = ((AopType) value).getAopTypeMeta();
                aopTypeMeta.setIocContext(this);
                aopTypeMeta.setBeanField(beanField); // beanField表示是属性懒加载代理对象,没有表示是Bean初始化
            }

            log.info("setInjectFieldAopTypeValue() INJECT_TYPE_REFERENCE, beanInstance=" + beanInstance + ", fieldName=" + fieldName /* + ", value=" + value */);
            setFieldValue(beanInstance, fieldName, value);
        } else {
            啊啊啊啊啊啊啊啊啊啊(beanType, beanInstance, beanField, fieldName);
        }
    }

    public void 啊啊啊啊啊啊啊啊啊啊(Class<?> beanType, Object beanInstance, BeanField beanField, String fieldName) {
        try {
            Field field = Reflect.getDeclaredField(beanType, fieldName);
            Object value = getConvertService().convert(String.class, field.getType(), beanField.getValue());
            Reflect.setFieldValue(beanInstance, field, value);
        } catch (Exception e) {
            List<Method> methods = Reflect.getPublicMethods(beanType);
            for (Method method : methods) {

            }
        }
    }

    private static void setFieldValue(Object beanInstance, String fieldName, Object value) {
        try {
            Field field = Reflect.getDeclaredField(beanInstance.getClass(), fieldName);
            Reflect.setFieldValue(beanInstance, field, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
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