package builder.util;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Freemarker 工具
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年1月8日 下午6:04:25)
 */
public class Fms {
    /**
     * 用Freemarker渲染模版返回String
     */
    public static String merge(final String templateSource, final Map<String, Object> data) {
        return write(templateSource, templateSource, data, new StringWriter()).toString();
    }

    /**
     * 用Freemarker渲染模版写到Writer
     */
    public static Writer write(String templateName, String templateSource, Map<String, Object> data, Writer writer) {
        try {
            Configuration configuration = new Configuration();
            StringTemplateLoader templateLoader = new StringTemplateLoader();
            templateLoader.putTemplate(templateName, templateSource);
            configuration.setTemplateLoader(templateLoader);
            Template template = configuration.getTemplate(templateName);
            data.put("def", BeansWrapper.getDefaultInstance().getStaticModels());// 静态方法调用支持,类似 ${def["java.lang.System"].currentTimeMillis()}
            template.process(data, writer);
            return writer;
        } catch (Exception e) {
            throw Errors.wrap(e);
        }
    }
}