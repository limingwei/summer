package summer.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author li
 * @version 1 (2015年10月12日 下午6:55:29)
 * @since Java7
 */
public interface ViewProcessor {
    public void process(HttpServletRequest request, HttpServletResponse response, Object result);
}