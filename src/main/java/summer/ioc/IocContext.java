package summer.ioc;

/**
 * @author li
 * @version 1 (2015年10月10日 上午9:52:36)
 * @since Java7
 */
public interface IocContext {
    public <T> T getBean(Class<T> type);

    public <T> T getBean(Class<T> type, String name);

    public Object getBean(String name);
}