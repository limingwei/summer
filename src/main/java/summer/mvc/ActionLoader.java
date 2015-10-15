package summer.mvc;

import java.util.Map;

/**
 * @author li
 * @version 1 (2015年10月15日 下午6:50:29)
 * @since Java7
 */
public interface ActionLoader {
    public Map<String, ActionHandler> getActionHandlerMapping();
}