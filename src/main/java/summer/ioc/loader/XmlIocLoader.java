package summer.ioc.loader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.ioc.IocLoader;
import summer.util.Reflect;
import summer.util.Xml;

/**
 * @author li
 * @version 1 (2015年10月10日 上午10:37:17)
 * @since Java7
 */
public class XmlIocLoader implements IocLoader {
    private ArrayList<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();

    public XmlIocLoader(InputStream inputStream) {
        Document document = Xml.parse(inputStream);
        NodeList nodeList = document.getElementsByTagName("bean");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            BeanDefinition beanDefinition = parseBean(node);
            beanDefinitions.add(beanDefinition);
        }
    }

    public BeanDefinition parseBean(Node node) {
        BeanDefinition beanDefinition = new BeanDefinition();

        NamedNodeMap attributes = node.getAttributes();

        Node typeNameNode = attributes.getNamedItem("class");
        String typeName = typeNameNode.getNodeValue();
        Class<?> beanType = Reflect.classForName(typeName);
        beanDefinition.setBeanType(beanType);

        String beanId = attributes.getNamedItem("id").getNodeValue();
        beanDefinition.setId(beanId);

        beanDefinition.setBeanFields(parseBeanFields(node));

        return beanDefinition;
    }

    private List<BeanField> parseBeanFields(Node node) {
        List<BeanField> beanFields = new ArrayList<BeanField>();
        NamedNodeMap attributes = node.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            String name = attribute.getNodeName();
            String value = attribute.getNodeValue();

            if (name.startsWith("p:")) {
                String fieldName = name.substring(2);
                beanFields.add(new BeanField(BeanField.INJECT_TYPE_VALUE, fieldName, value));
            } else if (name.startsWith("ref:")) {
                String fieldName = name.substring(4);
                beanFields.add(new BeanField(BeanField.INJECT_TYPE_REFERENCE, fieldName, value));
            }
        }

        return beanFields;
    }

    public List<BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }
}