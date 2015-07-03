package cn.limw.summer.spring.beans.factory.json;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;

import cn.limw.summer.util.Files;
import cn.limw.summer.util.Jsons;
import cn.limw.summer.util.StringUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author li
 * @version 1 (2014年12月11日 下午5:52:22)
 * @since Java7
 */
public class JsonToXmlBeanDefinitionReader extends XmlBeanDefinitionReader {
    public JsonToXmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public JsonToXmlBeanDefinitionReader(XmlBeanDefinitionReader reader) {
        super(reader.getRegistry());
    }

    protected int doLoadBeanDefinitions(InputSource inputSource, Resource resource) throws BeanDefinitionStoreException {
        return super.doLoadBeanDefinitions(transJsonToXml(inputSource), resource);
    }

    private InputSource transJsonToXml(InputSource inputSource) {
        String jsonConfig = Files.toString(inputSource.getByteStream());
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<beans xmlns=\"http://www.springframework.org/schema/beans\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xmlns:util=\"http://www.springframework.org/schema/util\" "
                + "xmlns:p=\"http://www.springframework.org/schema/p\" "
                + "xmlns:context=\"http://www.springframework.org/schema/context\" "
                + "xmlns:aop=\"http://www.springframework.org/schema/aop\" "
                + "xsi:schemaLocation=\"http://www.springframework.org/schema/beans "
                + "http://www.springframework.org/schema/beans/spring-beans.xsd "
                + "http://www.springframework.org/schema/util "
                + "http://www.springframework.org/schema/util/spring-util.xsd "
                + "http://www.springframework.org/schema/context "
                + "http://www.springframework.org/schema/context/spring-context.xsd "
                + "http://www.springframework.org/schema/aop "
                + "http://www.springframework.org/schema/aop/spring-aop.xsd \">";

        Map<String, Object> config = Jsons.toMap(jsonConfig);

        for (Entry<String, Object> entry : config.entrySet()) {
            if (isList(entry)) {
                xml += transList(entry);
            } else if (isMap(entry)) {
                xml += transMap(entry);
            } else {
                xml += transBeam(xml, entry);
            }
        }
        xml += "</beans>";

        return new InputSource(new ByteArrayInputStream(xml.getBytes()));
    }

    private boolean isMap(Entry<String, Object> entry) {
        return entry.getValue() instanceof JSONObject && StringUtil.isEmpty(JsonToXmlBeanDefinitionUtil.getBeanClass(entry));
    }

    private String transList(Entry<String, Object> entry) {
        return "<!-- list -->";
    }

    private boolean isList(Entry<String, Object> entry) {
        return entry.getValue() instanceof JSONArray;
    }

    private String transMap(Entry<String, Object> entry) {
        return "<!-- map -->";
    }

    private String transBeam(String xml, Entry<String, Object> entry) {
        String beanClass = JsonToXmlBeanDefinitionUtil.getBeanClass(entry);
        xml += "<bean id=\"" + JsonToXmlBeanDefinitionUtil.getBeanId(entry) + "\" class=\"" + beanClass + "\">"
                + JsonToXmlBeanDefinitionUtil.getProperties(entry)
                + "</bean>";
        return xml;
    }
}