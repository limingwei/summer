package summer.mvc;

import javax.servlet.http.HttpServletRequest;

/**
 * @author li
 * @version 1 (2015年10月12日 下午3:33:10)
 * @since Java7
 */
public interface MvcContext {
    public ActionHandler getActionHandler(HttpServletRequest request);
}