package cn.limw.summer.web.wro4j.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathConstants;

import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.GenericApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.limw.summer.spring.util.ResourceUtil;
import cn.limw.summer.util.Files;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Xmls;
import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @author li
 * @version 1 (2014年9月30日 下午4:39:12)
 * @since Java7
 */
public class DynamicImportResourceDirective extends DynamicImportJsOrCssDirective implements InitializingBean {
    private static final Logger log = Logs.slf4j();

    private String configFile = "wro.xml";

    public void afterPropertiesSet() throws Exception {
        init();
    }

    /**
     * org.springframework.web.context.support.XmlWebApplicationContext
     * org.springframework.context.support.GenericApplicationContext
     */
    public File getWroDirectory() throws IOException {
        if (getContext() instanceof GenericApplicationContext) {//junit
            return new File("src/main/webapp/source/wro");
        } else { // web启动
            return super.getWroDirectory();
        }
    }

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        dynamicImportResourceDirectiveExecute(env, params, loopVars, body);
    }

    public void dynamicImportResourceDirectiveExecute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) {
        try {
            String type = ((SimpleScalar) params.get("type")).getAsString();
            String filePath = ((SimpleScalar) params.get("filePath")).getAsString();

            env.getOut().write("\n<!-- resources type=" + type + ", filePath=" + filePath + " start -->\n");
            List<String> resources = getResources(type, filePath);
            for (String resource : resources) {
                writeResource(env.getOut(), type, resource);
            }
            env.getOut().write("<!-- resources type=" + type + ", filePath=" + filePath + " end -->\n\n");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getResources(String type, String filePath) {
        List<String> resources = new ArrayList<String>();
        Document xmlDoc = Xmls.buildByContent(Files.toString(ResourceUtil.classPathRead(getConfigFile()), "UTF-8"));
        Node docRoot = xmlDoc.getFirstChild();
        NodeList groupNodes = docRoot.getChildNodes();

        for (int length = (null == groupNodes ? -1 : groupNodes.getLength()), i = 0; i < length; i++) {
            Node item = groupNodes.item(i);
            String name = Xmls.xpath(item, "@name", XPathConstants.STRING) + "";
            if (filePath.equals("/source/wro/" + name)) {
                NodeList nodeList = item.getChildNodes();
                for (int len = (null == nodeList ? -1 : nodeList.getLength()), j = 0; j < len; j++) {
                    Node resource = nodeList.item(j);
                    if (resource.getNodeName().equals(type)) {
                        resources.add(resource.getFirstChild().getNodeValue());
                    }
                }
            } else {
                log.error("getResources() 资源未找到 filePath=" + filePath + ", name=" + name);
            }
        }
        return resources;
    }

    private void writeResource(Writer writer, String type, String textContent) throws IOException {
        if (textContent.startsWith("/")) {
            textContent = textContent.substring(1);
        }
        if ("css".equals(type)) {
            writer.write("<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"" + textContent + "\">\n");
        } else if ("js".equals(type)) {
            writer.write("<script type=\"text/javascript\" src=\"" + textContent + "\"></script>\n");
        }
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public String getConfigFile() {
        return configFile;
    }
}