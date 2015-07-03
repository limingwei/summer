package cn.limw.summer.spring.web.view.freemarker;

/**
 * @author li
 * @version 1 (2015年2月5日 上午9:37:39)
 * @since Java7
 */
public class AbstractFreeMarkerViewResolver extends org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver {
    protected Class<?> requiredViewClass() {
        return FreeMarkerView.class;
    }
}