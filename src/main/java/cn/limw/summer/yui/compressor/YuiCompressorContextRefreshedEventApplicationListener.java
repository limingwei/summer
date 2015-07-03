package cn.limw.summer.yui.compressor;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.WebApplicationContext;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年6月26日 下午4:50:36)
 * @since Java7
 */
public class YuiCompressorContextRefreshedEventApplicationListener implements ApplicationListener<ApplicationEvent> {
    private static final Logger log = Logs.slf4j();

    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ContextRefreshedEvent) {
            ContextRefreshedEvent contextRefreshedEvent = (ContextRefreshedEvent) applicationEvent;
            ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
            if (applicationContext instanceof WebApplicationContext) {
                WebApplicationContext webApplicationContext = (WebApplicationContext) applicationContext;
                ServletContext servletContext = webApplicationContext.getServletContext();
                WroYuiUtil.compressResources(servletContext);
            } else {
                log.info("not WebApplicationContext {}", applicationContext);
            }
        } else {
            log.info("not ContextRefreshedEvent {}", applicationEvent);
        }
    }
}