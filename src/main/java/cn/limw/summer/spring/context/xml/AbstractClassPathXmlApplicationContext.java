package cn.limw.summer.spring.context.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author li
 * @version 1 (2015年4月28日 上午11:00:57)
 * @since Java7
 */
public class AbstractClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {
    public AbstractClassPathXmlApplicationContext(String[] configLocations) {
        super(configLocations);
    }
}