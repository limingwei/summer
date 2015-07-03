package cn.limw.summer.spring.web.view.meta.fastjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.spring.web.servlet.Mvcs;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;

/**
 * @author li
 * @version 1 (2015年4月1日 下午1:58:30)
 * @since Java7
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class FastJsonMetaView extends FastJsonJsonView {
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.putAll(model);
        requestMap.putAll(Mvcs.getRequestAttributes(request));

        Map<String, Object> sessionMap = new HashMap<String, Object>();
        sessionMap.putAll(Mvcs.getSessionMap());

        Map<String, Object> servletContextMap = new HashMap<String, Object>();
        servletContextMap.putAll(Mvcs.getServletContextAttributes(Mvcs.getServletContext()));

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("viewName", request.getAttribute("Dispatcher.resolveViewName"));
        map.put("requestMap", requestMap);
        map.put("sessionMap", sessionMap);
        map.put("servletContextMap", servletContextMap);
        map.put("requestURI", request.getRequestURI());

        super.renderMergedOutputModel(map, request, response);
    }

    protected Object filterModel(Map<String, Object> model) {
        return filterMap(model);
    }

    protected Object filterMap(Map model) {
        Map result = new HashMap();
        for (Object each : model.entrySet()) {
            Entry entry = (Entry) each;
            Object key = entry.getKey();
            if (filterKey(key)) {
                result.put(key, filterValue(entry.getValue()));
            }
        }
        return result;
    }

    private boolean filterKey(Object key) {
        if ("org.apache.catalina.jsp_classpath".equals(key + "")) {
            return false;
        } else if ("org.apache.tomcat.util.scan.MergedWebXml".equals(key + "")) {
            return false;
        } else {
            return true;
        }
    }

    private Object filterValue(Object value) {
        if (null == value) {
            return null;
        } else if (value instanceof Map) {
            return filterMap((Map) value);
        } else if (value instanceof Collection) {
            return filterCollection((Collection) value);
        } else if (value instanceof String) {
            return value;
        } else if (value instanceof Boolean) {
            return value;
        } else if (value.getClass().getName().startsWith("entity")) {
            return value;
        } else if (value.getClass().getName().startsWith("entity")) {
            return value;
        } else if (value.getClass().getName().startsWith("entity")) {
            return value;
        } else if (value.getClass().getName().startsWith("entity")) {
            return value;
        } else if (value.getClass().getName().startsWith("entity")) {
            return value;
        } else {
            logger.info("filterValue return null type=" + value.getClass());
            return null;
        }
    }

    private Object filterCollection(Collection value) {
        List result = new ArrayList();
        for (Object each : value) {
            result.add(filterValue(each));
        }
        return result;
    }
}