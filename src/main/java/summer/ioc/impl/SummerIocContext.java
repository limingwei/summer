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
import summer.converter.impl.SummerConvertService;
import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.ioc.IocContext;
import summer.ioc.IocContextAware;
import summer.ioc.IocLoader;
import summer.ioc.SummerCompiler;
import summer.ioc.compiler.CachedSummerCompiler;
import summer.ioc.compiler.JavassistSummerCompiler;
import summer.ioc.compiler.JavassistSummerCompilerUtil;
import summer.ioc.impl.util.SummerIocContextUtil;
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

    public IocLoader getIocLoader() {
        return iocLoader;
    }

    public SummerIocContext(IocLoader iocLoader) {
        log.info("\n\n\thttps://github.com/limingwei/summer\n");

        this.iocLoader = iocLoader;

        this.beanDefinitions = SummerIocContextUtil.mergeBeanDefinitions(iocLoader.getBeanDefinitions());

        this.convertService = new SummerConvertService();

        this.beanInstances = new HashMap<BeanDefinition, Object>();

        this.summerCompiler = new CachedSummerCompiler(new JavassistSummerCompiler());

        log.info("SummerIocContext inited, iocLoader={}, beanDefinitions={}, convertService={}", iocLoader, beanDefinitions, convertService);
    }

    public List<BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    public <T> T doGetBean(Class<T> type, String id) {
        if (IocContext.class.equals(type)) {
            return (T) this;
        } else {
            BeanDefinition beanDefinition = SummerIocContextUtil.findMatchBeanDefinition(beanDefinitions, type, id);
            Assert.noNull(beanDefinition, "not found BeanDefinition for type " + type + ", id=" + id + ", beanDefinitions=" + beanDefinitions);

            return (T) SummerIocContextUtil.unwrapFactoryBean(getBeanInstance(beanDefinition));
        }
    }

    private synchronized Object getBeanInstance(BeanDefinition beanDefinition) {
        Object instance = beanInstances.get(beanDefinition);
        if (null == instance) { // 延迟初始化
            Class<?> beanType = beanDefinition.getBeanType();

            // TODO 先生成代理, 代理加上Aop, 然后实例化
            Object beanInstance = newBeanInstance(beanType);

            for (BeanField beanField : beanDefinition.getBeanFields()) {
                if (BeanField.INJECT_TYPE_REFERENCE.equals(beanField.getInjectType())) {
                    Field field = Reflect.getDeclaredField(beanType, beanField.getName());
                    Object value = newReferenceInstance(beanDefinition, beanField);

                    if (value instanceof AopType) {
                        AopTypeMeta aopTypeMeta = ((AopType) value).getAopTypeMeta();
                        aopTypeMeta.setIocContext(this);
                        aopTypeMeta.setReferenceName(BEAN_HAS_NO_ID);
                        aopTypeMeta.setReferenceType(field.getType());
                    }

                    Reflect.setFieldValue(beanInstance, field, value);
                } else {
                    Field field = Reflect.getDeclaredField(beanType, beanField.getName());
                    Object value = convertService.convert(String.class, field.getType(), beanField.getValue());
                    Reflect.setFieldValue(beanInstance, field, value);
                }
            }

            if (beanInstance instanceof IocContextAware) {
                ((IocContextAware) beanInstance).setIocContext(this);
            }

            if (beanInstance instanceof AopType) {
                AopTypeMeta aopTypeMeta = ((AopType) beanInstance).getAopTypeMeta();
                aopTypeMeta.setIocContext(this);
                Map<String, Method> map = aopTypeMeta.getMethodMap();

                List<Method> methods = Reflect.getPublicMethods(beanDefinition.getBeanType());
                for (Method method : methods) {
                    map.put(JavassistSummerCompilerUtil.getMethodSignature(method), method);
                }
            }

            log.info("inited bean " + beanDefinition.getId() + ", " + beanDefinition.getBeanType() + ", beanInstance=" + beanInstance);

            beanInstances.put(beanDefinition, beanInstance);
            instance = beanInstance;
        }
        return instance;
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