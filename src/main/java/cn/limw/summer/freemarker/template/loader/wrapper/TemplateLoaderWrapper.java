package cn.limw.summer.freemarker.template.loader.wrapper;

import java.io.IOException;
import java.io.Reader;

import cn.limw.summer.util.Asserts;
import freemarker.cache.TemplateLoader;

/**
 * @author li
 * @version 1 (2015年1月8日 上午10:28:09)
 * @since Java7
 */
public class TemplateLoaderWrapper implements TemplateLoader {
    private TemplateLoader templateLoader;

    public TemplateLoaderWrapper() {}

    public TemplateLoaderWrapper(TemplateLoader templateLoader) {
        setTemplateLoader(templateLoader);
    }

    public TemplateLoader getTemplateLoader() {
        return Asserts.noNull(templateLoader);
    }

    public TemplateLoaderWrapper setTemplateLoader(TemplateLoader templateLoader) {
        this.templateLoader = templateLoader;
        return this;
    }

    public Object findTemplateSource(String name) throws IOException {
        return getTemplateLoader().findTemplateSource(name);
    }

    public long getLastModified(Object templateSource) {
        return getTemplateLoader().getLastModified(templateSource);
    }

    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return getTemplateLoader().getReader(templateSource, encoding);
    }

    public void closeTemplateSource(Object templateSource) throws IOException {
        getTemplateLoader().closeTemplateSource(templateSource);
    }
}