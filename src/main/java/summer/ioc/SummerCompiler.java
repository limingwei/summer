package summer.ioc;

/**
 * @author li
 * @version 1 (2015年10月11日 上午9:50:31)
 * @since Java7
 */
public interface SummerCompiler {
    public Class<?> compile(Class<?> originalType);

    public Class<?> compileReferenceType(BeanDefinition beanDefinition, BeanField beanField);
}