package cn.limw.summer.freemarker.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import cn.limw.summer.java.util.PropertiesUtil;
import cn.limw.summer.util.Files;
import cn.limw.summer.util.Maps;
import freemarker.cache.StringTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;

/**
 * @author li
 * @version 1 (2014年6月27日 下午4:19:51)
 * @since Java7
 */
public class FreeMarkerUtil {
    private static final Properties FREEMARKER_PROPERTIES = PropertiesUtil.fromMapToProperties(Maps.toMap(//
            "number_format", "0",// 
            "default_encoding", "UTF-8",//
            "output_encoding", "UTF-8",//
            "locale", "zh_CN", //
            "date_format", "yyyy-MM-dd",// 
            "time_format", "HH:mm:ss", //
            "datetime_format", "yyyy-MM-dd HH:mm:ss",// 
            "tag_syntax", "auto_detect",//
            "template_update_delay", "60"//
    ));

    private static final TemplateHashModel STATIC_MODELS = BeansWrapper.getDefaultInstance().getStaticModels();

    private static final Map<String, Template> TEMPLATE_CACHE = new ConcurrentHashMap<String, Template>();

    public static Template getTemplate(String templateSource) {
        String templateName = "t-" + templateSource;
        Template template = TEMPLATE_CACHE.get(templateName);
        if (null == template) {
            TEMPLATE_CACHE.put(templateName, template = doGetTemplate(templateSource));
        }
        return template;
    }

    public static Template doGetTemplate(String templateSource) {
        try {
            String templateName = "t-" + templateSource;
            Configuration configuration = new Configuration();
            configuration.setSettings(FREEMARKER_PROPERTIES);
            StringTemplateLoader templateLoader = new StringTemplateLoader();
            configuration.setTemplateLoader(templateLoader);
            templateLoader.putTemplate(templateName, templateSource);
            return configuration.getTemplate(templateName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String merge(Template template, Map data) {
        try {
            Map map = new HashMap(data);
            map.put("def", STATIC_MODELS);// 静态方法调用支持,类似 ${def["java.lang.System"].currentTimeMillis()}
            Writer writer = new StringWriter();
            template.process(map, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() + ", template=" + template + ", data=" + Maps.toString(data), e);
        }
    }

    public static String merge(String templateSource, Map data) {
        return merge(getTemplate(templateSource), data);
    }

    public static String merge(InputStream inputStream, Map data) {
        return merge(Files.toString(inputStream), data);
    }

    public static void merge(InputStream inputStream, Map data, OutputStream outputStream) {
        Template template = null;
        try {
            template = getTemplate(Files.toString(inputStream, "UTF-8"));
            template.process(Maps.append(data, "def", STATIC_MODELS), new OutputStreamWriter(outputStream));// 静态方法调用支持,类似 ${def["java.lang.System"].currentTimeMillis()}
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() + ", template=" + template + ", data=" + Maps.toString(data), e);
        }
    }
}