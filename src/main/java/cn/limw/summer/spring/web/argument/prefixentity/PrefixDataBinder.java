package cn.limw.summer.spring.web.argument.prefixentity;

import javax.servlet.ServletRequest;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;
import org.springframework.web.util.WebUtils;

/**
 * @author li
 * @version 1 (2014年7月5日 下午12:00:54)
 * @since Java7
 */
public class PrefixDataBinder extends ExtendedServletRequestDataBinder {
    private String prefix;

    public PrefixDataBinder(ExtendedServletRequestDataBinder binder, String prefix) {
        super(binder.getTarget(), binder.getObjectName());
        this.prefix = prefix;
    }

    public void bind(ServletRequest request) {
        MutablePropertyValues mpvs = new ServletRequestParameterPropertyValues(request, prefix, ".");
        MultipartRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartRequest.class);
        if (multipartRequest != null) {
            bindMultipart(multipartRequest.getMultiFileMap(), mpvs);
        }
        addBindValues(mpvs, request);
        doBind(mpvs);
    }
}