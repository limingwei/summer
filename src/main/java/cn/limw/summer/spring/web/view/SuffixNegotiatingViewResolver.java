package cn.limw.summer.spring.web.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.View;

/**
 * @author li
 * @version 1 (2014年11月11日 上午11:40:54)
 * @since Java7
 */
public class SuffixNegotiatingViewResolver extends AbstractContentNegotiatingViewResolver {
    private Map<String, View> viewsBySuffix = new HashMap<String, View>();

    public View getBestView(List<View> candidateViews, List<MediaType> requestedMediaTypes, RequestAttributes requestAttributes) {
        View bestView = null;
        bestView = getBestViewBySuffix(((ServletRequestAttributes) requestAttributes).getRequest());
        if (null == bestView) {
            bestView = super.getBestView(candidateViews, requestedMediaTypes, requestAttributes);
        }
        return bestView;
    }

    private View getBestViewBySuffix(HttpServletRequest request) {
        for (Entry<String, View> entry : getViewsBySuffix().entrySet()) {
            if (request.getRequestURI().endsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Map<String, View> getViewsBySuffix() {
        return viewsBySuffix;
    }

    public void setViewsBySuffix(Map<String, View> viewsBySuffix) {
        this.viewsBySuffix = viewsBySuffix;
    }
}