package cn.limw.summer.freemarker.template.exception.handler;

import java.io.Writer;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;
import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author li
 * @version 1 (2015年1月23日 下午1:09:02)
 * @since Java7
 */
public class PrintStackTraceTemplateExceptionHandler implements TemplateExceptionHandler {
    private static final Logger log = Logs.slf4j();

    public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
        log.error("PrintStackTraceTemplateExceptionHandler.handleTemplateException " + te, te);
    }
}