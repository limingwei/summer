package summer.ioc.compiler;

import java.util.HashMap;
import java.util.Map;

import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.ioc.SummerCompiler;

/**
 * @author li
 * @version 1 (2015年10月11日 下午6:51:28)
 * @since Java7
 */
public class CachedSummerCompiler implements SummerCompiler {
    private static final Map<Class<?>, Class<?>> COMPILE_CLASS_CACHE_MAP = new HashMap<Class<?>, Class<?>>();

    private static final Map<String, Class<?>> COMPILE_REFERENCE_CACHE_MAP = new HashMap<String, Class<?>>();

    private SummerCompiler summerCompiler;

    public CachedSummerCompiler(SummerCompiler summerCompiler) {
        this.summerCompiler = summerCompiler;
    }

    public Class<?> compileClass(Class<?> originalType) {
        Class<?> result = COMPILE_CLASS_CACHE_MAP.get(originalType);
        if (null == result) {
            COMPILE_CLASS_CACHE_MAP.put(originalType, result = summerCompiler.compileClass(originalType));
        }
        return result;
    }

    public Class<?> compileReference(BeanDefinition beanDefinition, BeanField beanField) {
        String key = beanDefinition.getBeanType() + "_" + beanDefinition.getId() + "_" + beanField.getName();
        Class<?> result = COMPILE_REFERENCE_CACHE_MAP.get(key);
        if (null == result) {
            COMPILE_REFERENCE_CACHE_MAP.put(key, result = summerCompiler.compileReference(beanDefinition, beanField));
        }
        return result;
    }
}