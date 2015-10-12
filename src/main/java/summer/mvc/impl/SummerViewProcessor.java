package summer.mvc.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import summer.mvc.Mvc;
import summer.mvc.ViewProcessor;

/**
 * @author li
 * @version 1 (2015年10月12日 下午4:11:20)
 * @since Java7
 */
public class SummerViewProcessor implements ViewProcessor {
    public void process(HttpServletRequest request, HttpServletResponse response, Object result) {
        if (null != result && result instanceof String) {
            String resultString = (String) result;
            if (resultString.startsWith("redirect:")) {
                Mvc.redirect(resultString.substring(9));
            } else if (resultString.startsWith("forward:")) {
                Mvc.forward(resultString.substring(8));
            }
        }
    }
}