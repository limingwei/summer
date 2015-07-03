package cn.limw.summer.spring.web.view.xstream;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import cn.limw.summer.util.Mirrors;
import cn.limw.summer.xstream.converter.TimeConverter;

import com.thoughtworks.xstream.XStream;

/**
 * XmlView 扩展自 XStreamXmlView 支持更多配置
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月11日 下午8:40:32)
 */
public class XStreamXmlView extends AbstractView {
    public static final String DEFAULT_CONTENT_TYPE = "application/xml";

    public final static Charset UTF8 = Charset.forName("UTF-8");

    private boolean disableCaching = true;

    private XStream xStream;

    /* 实例代码块 */
    {
        super.setContentType(DEFAULT_CONTENT_TYPE);
        super.setExposePathVariables(false);
        this.xStream = new XStream();
        this.xStream.registerConverter(new TimeConverter());
    }

    public void setMode(String mode) {
        xStream.setMode((int) Mirrors.getFieldValue(XStream.class, mode, null));
    }

    /**
     * 设置包别名
     */
    public void setAliasPackage(Map<String, String> aliasPackage) {
        for (Entry<String, String> entry : aliasPackage.entrySet()) {
            this.xStream.aliasPackage(entry.getValue(), entry.getKey());
        }
    }

    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.xStream.toXML(this.filter(model), byteArrayOutputStream);
        super.writeToResponse(response, byteArrayOutputStream);
    }

    private Map<String, Object> filter(Map<String, Object> model) {
        Map<String, Object> values = new HashMap<String, Object>();
        Set<Entry<String, Object>> set = model.entrySet();
        for (Entry<String, Object> entry : set) {
            Object value = entry.getValue();
            values.put(entry.getKey(), value);
        }
        return values;
    }

    protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
        setResponseContentType(request, response);
        response.setCharacterEncoding(UTF8.name());
        if (this.disableCaching) {
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
            response.addDateHeader("Expires", 1L);
        }
    }

    public void setDisableCaching(boolean disableCaching) {
        this.disableCaching = disableCaching;
    }
}