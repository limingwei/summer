package summer.ioc;

import java.util.List;

/**
 * @author li
 * @version 1 (2015年10月10日 上午9:52:36)
 * @since Java7
 */
public interface IocContext {
    static final String BEAN_HAS_NO_ID = "BEAN_HAS_NO_ID";

    public Object getBean(String id);

    public <T> T getBean(Class<T> type);

    public <T> T getBean(Class<T> type, String id);

    public Boolean containsBean(String id);

    public Boolean containsBean(Class<?> type);

    public Boolean containsBean(Class<?> type, String id);

    public List<BeanDefinition> getBeanDefinitions();
}