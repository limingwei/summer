package cn.limw.summer.webx.context;

/**
 * @author li
 * @version 1 (2015年6月9日 下午4:41:24)
 * @since Java7
 */
public class WebxComponentsLoader extends AbstractWebxComponentsLoader {
    public static final WebxComponentsLoader INSTANCE = new WebxComponentsLoader() {
        protected java.lang.Class<? extends com.alibaba.citrus.webx.context.WebxComponentsContext> getDefaultContextClass() {
            return WebxComponentsContext.class;
        };
    };
}