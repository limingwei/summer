package cn.limw.summer.spring.web.view.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.util.Maps;
import freemarker.ext.beans.BeansWrapper;

/**
 * 扩展 了 SpringMvc FreeMarkerView 使支持静态方法调用
 * @author li
 * @version 1 (2014年7月17日 下午12:43:17)
 * @since Java7
 */
public class FreeMarkerView extends AbstractFreeMarkerView {
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object staticModels = BeansWrapper.getDefaultInstance().getStaticModels();
        Maps.put(model, "def", staticModels); // 静态方法调用支持,类似 ${def["java.lang.System"].currentTimeMillis()}
        super.render(model, request, response);
    }
}