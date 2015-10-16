package summer.ioc.loader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.ioc.IocLoader;
import summer.log.Logger;
import summer.util.Log;
import summer.util.Reflect;
import summer.util.Xml;

/**
 * @author li
 * @version 1 (2015年10月10日 上午10:37:17)
 * @since Java7
 */
public class XmlIocLoader implements IocLoader {
    private static final Logger log = Log.slf4j();

    private InputStream inputStream;

    public XmlIocLoader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<BeanDefinition> getBeanDefinitions() {
        ArrayList<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
        Document document = Xml.parse(inputStream);
        NodeList nodeList = document.getElementsByTagName("bean");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            BeanDefinition beanDefinition = parseBean(node);
            beanDefinitions.add(beanDefinition);

            log.debug("add xml bean {} {}", beanDefinition.getBeanType().getName(), beanDefinition.getId());
        }
        log.info("getBeanDefinitions() returning {}", beanDefinitions.size());
        return beanDefinitions;
    }

    private static BeanDefinition parseBean(Node node) {
        BeanDefinition beanDefinition = new BeanDefinition();

        NamedNodeMap attributes = node.getAttributes();

        Node typeNameNode = attributes.getNamedItem("class");
        String typeName = typeNameNode.getNodeValue();
        Class<?> beanType = Reflect.classForName(typeName);
        beanDefinition.setBeanType(beanType);

        Node idNode = attributes.getNamedItem("id");
        String beanId;
        if (null == idNode) {
            beanId = beanType.getName(); // 默认Bean名称为type.name
        } else {
            beanId = idNode.getNodeValue();
        }

        beanDefinition.setId(beanId);

        beanDefinition.setBeanFields(parseBeanFields(node, beanDefinition));

        return beanDefinition;
    }

    private static List<BeanField> parseBeanFields(Node node, BeanDefinition beanDefinition) {
        List<BeanField> beanFields = new ArrayList<BeanField>();
        NamedNodeMap attributes = node.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            String name = attribute.getNodeName();
            String value = attribute.getNodeValue();

            if (name.startsWith("p:")) {
                if (name.endsWith("-ref")) {
                    String fieldName = name.substring(2, name.length() - 4);
                    BeanField beanField = new BeanField(BeanField.INJECT_TYPE_REFERENCE, fieldName, value);

                    Class<?> fieldType = getFieldType(beanDefinition.getBeanType(), fieldName);

                    beanField.setType(fieldType);
                    beanFields.add(beanField);
                } else {
                    String fieldName = name.substring(2);
                    BeanField beanField = new BeanField(BeanField.INJECT_TYPE_VALUE, fieldName, value);

                    Class<?> fieldType = getFieldType(beanDefinition.getBeanType(), fieldName);

                    beanField.setType(fieldType);
                    beanFields.add(beanField);
                }
            } else if (name.startsWith("ref:")) {
                String fieldName = name.substring(4);
                BeanField beanField = new BeanField(BeanField.INJECT_TYPE_REFERENCE, fieldName, value);

                Class<?> fieldType = getFieldType(beanDefinition.getBeanType(), fieldName);

                beanField.setType(fieldType);
                beanFields.add(beanField);
            }
        }

        return beanFields;
    }

    public static Class<?> getFieldType(Class<?> targetType, String fieldName) {
        try {
            return getFieldTypeByField(targetType, fieldName);
        } catch (Exception e) {
            return getFieldTypeBySetter(targetType, fieldName);
        }
    }

    private static Class<?> getFieldTypeBySetter(Class<?> targetType, String fieldName) {
        String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

        List<Method> methods = Reflect.getPublicMethods(targetType);
        for (Method method : methods) {
            if (method.getName().equals(setterName)) {
                return method.getParameterTypes()[0];
            }
        }
        throw new RuntimeException("not found " + setterName + " method on " + targetType);
    }

    private static Class<?> getFieldTypeByField(Class<?> targetType, String fieldName) {
        Field declaredField = Reflect.getDeclaredField(targetType, fieldName);
        Class<?> fieldType = declaredField.getType();
        return fieldType;
    }
}