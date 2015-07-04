package builder.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Xmls
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年1月8日 下午4:58:57)
 */
public class Xmls {
    /**
     * 根据文件路径path构建一个org.w3c.dom.Document
     */
    public static Document build(InputStream inputStream) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            return documentBuilderFactory.newDocumentBuilder().parse(inputStream);
        } catch (Exception e) {
            throw Errors.wrap(e);
        }
    }

    /**
     * 根据xpath表达式和returnType从document中读取值
     */
    public static Object xpath(Object document, String xpath, QName returnType) {
        try {
            return XPathFactory.newInstance().newXPath().compile(xpath).evaluate(document, returnType);
        } catch (Exception e) {
            throw Errors.wrap(e);
        }
    }

    /**
     * xpath计算返回字符串
     */
    public static String string(Object document, String xpath) {
        return (String) xpath(document, xpath, XPathConstants.STRING);
    }

    /**
     * xpath计算返回结点集合
     */
    public static NodeList nodes(Object document, String xpath) {
        return (NodeList) xpath(document, xpath, XPathConstants.NODESET);
    }

    /**
     * xpath计算返回单个结点
     */
    public static Node node(Object document, String xpath) {
        return (Node) xpath(document, xpath, XPathConstants.NODE);
    }

    /**
     * 以Map形式返回一个结点上的所有属性
     */
    public static Map<String, Object> attributes(Node node) {
        NamedNodeMap namedNodeMap = node.getAttributes();
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < namedNodeMap.getLength(); i++) {
            Node item = namedNodeMap.item(i);
            map.put(item.getNodeName(), item.getNodeValue());
        }
        return map;
    }
}
