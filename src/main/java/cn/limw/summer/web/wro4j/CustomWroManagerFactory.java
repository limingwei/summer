package cn.limw.summer.web.wro4j;

import ro.isdc.wro.manager.factory.standalone.DefaultStandaloneContextAwareManagerFactory;

/**
 * @author lgb
 * @version 1 (2014年9月26日下午4:19:28)
 * @since Java7
 */
public class CustomWroManagerFactory extends DefaultStandaloneContextAwareManagerFactory {
    public CustomWroManagerFactory() {
        setNamingStrategy(new CustomTimestampNamingStrategy());
    }
}