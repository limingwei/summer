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
import summer.ioc.IocContextAware;
import summer.ioc.IocLoader;
import summer.ioc.SummerCompiler;
import summer.ioc.compiler.CachedSummerCompiler;
import summer.ioc.compiler.JavassistSummerCompiler;
import summer.ioc.compiler.util.JavassistSummerCompilerUtil;
import summer.ioc.impl.util.SummerIocContextUtil;
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

        processFactoryBeans();

        initBeans();

        log.info("SummerIocContext inited, iocLoader={}", iocLoader);
    }

    /**
     * 初始化配置为需要预先初始化的Bean
     */
    private void initBeans() {}

    /**
     * 执行FactoryBean获得objectType,设置到beanDefinition
     */
    private void processFactoryBeans() {}

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
                if (BeanField.INJECT_TYPE_REFERENCE.equals(beanField.getInjectType())) { // 判断是否已经初始化, 如果已经初始化, 就直接将Bean注入
                    Field field = Reflect.getDeclaredField(beanType, beanField.getName());

                    Object value = newBeanInstance(field.getType());
                    AopTypeMeta aopTypeMeta = ((AopType) value).getAopTypeMeta();
                    aopTypeMeta.setIocContext(this);
                    aopTypeMeta.setBeanField(beanField); // beanField表示是属性懒加载代理对象,没有表示是Bean初始化

                    Reflect.setFieldValue(beanInstance, field, value);
                } else {
                    Field field = Reflect.getDeclaredField(beanType, beanField.getName());
                    Object value = getConvertService().convert(String.class, field.getType(), beanField.getValue());
                    Reflect.setFieldValue(beanInstance, field, value);
                }
            }

            if (beanInstance instanceof IocContextAware) {
                ((IocContextAware) beanInstance).setIocContext(this);
            }

            if (beanInstance instanceof AopType) {
                AopTypeMeta aopTypeMeta = ((AopType) beanInstance).getAopTypeMeta();
                aopTypeMeta.setIocContext(this);
                aopTypeMeta.setTarget(beanInstance); // 当前Bean实例

                List<Method> methods = Reflect.getPublicMethods(beanDefinition.getBeanType());
                for (Method method : methods) {
                    String methodSignature = JavassistSummerCompilerUtil.getMethodSignature(method);
                    aopTypeMeta.getMethodMap().put(methodSignature, method);
                }
            }

            log.info("inited bean " + beanDefinition.getId() + ", " + beanDefinition.getBeanType() + ", beanInstance=" + beanInstance);

            beanInstances.put(beanDefinition, beanInstance);
            instance = beanInstance;
        }
        return instance;
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