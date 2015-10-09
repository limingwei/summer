package summer.ioc;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:40:10)
 * @since Java7
 */
public interface FactoryBean<T> {
    public T getObject();
}