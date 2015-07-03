package cn.limw.summer.spring.web.servlet.config;

import org.springframework.web.servlet.config.MvcNamespaceHandler;

import cn.limw.summer.spring.web.servlet.config.interceptor.InterceptorsBeanDefinitionParser;

/**
 * <spring-mvc:interceptors-ref/>
 * @author li
 * @version 1 (2015年6月5日 上午10:01:13)
 * @since Java7
 */
public class SpringMvcNamespaceHandler extends MvcNamespaceHandler {
    public void init() {
        super.init();
        registerBeanDefinitionParser("interceptors-ref", new InterceptorsBeanDefinitionParser());
    }
}