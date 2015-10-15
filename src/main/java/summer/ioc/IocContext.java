package summer.ioc;

import java.util.List;

/**
 * @author li
 * @version 1 (2015年10月10日 上午9:52:36)
 * @since Java7
 */
public interface IocContext {
    public static final String DEFAULT_PARAMETER_ADAPTER_BEAN_ID = "parameterAdapter";

    public static final String DEFAULT_VIEW_PROCESSOR_BEAN_ID = "viewProcessor";

    public static final String DEFAULT_TRANSACTION_AOP_FILTER_BEAN_ID = "transactionAopFilter";

    public Object getBean(String id);

    public <T> T getBean(Class<T> type);

    public <T> T getBean(Class<T> type, String id);

    public Boolean containsBean(String id);

    public Boolean containsBean(Class<?> type);

    public Boolean containsBean(Class<?> type, String id);

    public List<BeanDefinition> getBeanDefinitions();
}