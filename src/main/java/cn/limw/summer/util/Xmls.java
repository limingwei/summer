package cn.limw.summer.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.w3c.dom.Document;

/**
 * @author li
 * @version 1 (2014年9月30日 下午4:44:51)
 * @since Java7
 */
public class Xmls {
    private static final Logger log = Logs.slf4j();

    public static final QName NODESET = XPathConstants.NODESET, //
            BOOLEAN = XPathConstants.BOOLEAN,//
            NODE = XPathConstants.NODE, //
            NUMBER = XPathConstants.NUMBER,//
            STRING = XPathConstants.STRING;

    public static Document build(InputStream inputStream) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            return documentBuilderFactory.newDocumentBuilder().parse(inputStream);
        } catch (Exception e) {
            log.error("{} Xmls.build()", e);
            return null;
        }
    }

    /**
     * 根据文件路径path构建一个org.w3c.dom.Document
     * @param path XML文件路径,相对于classPath的相对路径
     */
    public static Document build(String path) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            return documentBuilderFactory.newDocumentBuilder().parse(path);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static Document buildByContent(String content) {
        return build(new ByteArrayInputStream(content.getBytes()));
    }

    /**
     * 根据xpath表达式和returnType从document中读取值
     * @param document 被XPath解析的对象,Object类型,可以是Document,NodeList等
     * @param returnType XPathConstants枚举中的值,表示返回类型
     */
    public static Object xpath(Object document, String xpath, QName returnType) {
        try {
            return XPathFactory.newInstance().newXPath().compile(xpath).evaluate(document, returnType);
        } catch (Exception e) {
            log.error("{} Xmls.xpath() document:{} xpath:{} returnType:{}", e, document, xpath, returnType);
            return null;
        }
    }

    /**
     * 格式化Xml
     */
    public static String format(String xml) {
        try {
            SAXReader saxReader = new SAXReader(); // 注释：创建一个串的字符输入流
            org.dom4j.Document document = saxReader.read(new StringReader(xml)); // 注释：创建输出格式
            OutputFormat outputFormat = OutputFormat.createPrettyPrint(); // 注释：设置xml的输出编码
            outputFormat.setEncoding("UTF-8"); // 注释：创建输出(目标)
            StringWriter stringWriter = new StringWriter(); // 注释：创建输出流
            XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat); // 注释：输出格式化的串到目标中，执行后。格式化后的串保存在out中。
            xmlWriter.write(document);
            xmlWriter.close();
            return stringWriter.toString();// 注释：返回我们格式化后的结果
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String string(Document document, String xpath) {
        return xpath(document, xpath, XPathConstants.STRING) + "";
    }

}