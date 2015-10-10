package summer.ioc;

import java.util.List;

/**
 * @author li
 * @version 1 (2015年10月10日 上午9:52:28)
 * @since Java7
 */
public interface IocLoader {
    public List<BeanDefinition> getBeanDefinitions();
}