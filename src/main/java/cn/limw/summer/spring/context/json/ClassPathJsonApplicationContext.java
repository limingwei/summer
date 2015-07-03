package cn.limw.summer.spring.context.json;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.limw.summer.spring.beans.factory.json.JsonToXmlBeanDefinitionReader;

/**
 * @author li
 * @version 1 (2014年12月10日 下午1:23:38)
 * @since Java7
 * @see org.springframework.context.support.ClassPathXmlApplicationContext
 */
public class ClassPathJsonApplicationContext extends ClassPathXmlApplicationContext {
    public ClassPathJsonApplicationContext() {}

    public ClassPathJsonApplicationContext(String... configLocations) throws BeansException {
        super(configLocations, true, null);
    }

    protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException {
        super.loadBeanDefinitions(new JsonToXmlBeanDefinitionReader(reader));
    }
}