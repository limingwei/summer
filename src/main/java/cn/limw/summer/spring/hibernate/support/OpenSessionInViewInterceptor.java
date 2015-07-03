package cn.limw.summer.spring.hibernate.support;

import org.springframework.dao.DataAccessException;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;

/**
 * @author li
 * @version 1 (2015年5月6日 下午5:34:42)
 * @since Java7
 */
public class OpenSessionInViewInterceptor extends AbstractOpenSessionInViewInterceptor {
    public void preHandle(WebRequest request) throws DataAccessException {
        if (null == getSessionFactory()) {
            // 
        } else {
            super.preHandle(request);
        }
    }

    public void postHandle(WebRequest request, ModelMap model) {
        if (null == getSessionFactory()) {
            // 
        } else {
            super.postHandle(request, model);
        }
    }

    public void afterCompletion(WebRequest request, Exception ex) throws DataAccessException {
        if (null == getSessionFactory()) {
            // 
        } else {
            super.afterCompletion(request, ex);
        }
    }
}