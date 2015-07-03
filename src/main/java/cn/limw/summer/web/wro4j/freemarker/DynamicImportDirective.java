package cn.limw.summer.web.wro4j.freemarker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.util.StringUtil;
import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 新的
 * @author li
 * @version 1 (2015年4月2日 上午11:42:17)
 * @since Java7
 */
public class DynamicImportDirective extends SmartImportResourceDirective {
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        params.put("filePath", new SimpleScalar("/source/wro/" + params.get("group")));
        super.execute(env, params, loopVars, body);
    }

    public void dynamicImportJsOrCssDirectiveExecute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) {
        try {
            String result = "";
            String type = null;
            String filePath = null;
            if (params.get("type") != null) {
                type = ((SimpleScalar) params.get("type")).getAsString();
            }
            if (params.get("filePath") != null) {
                filePath = ((SimpleScalar) params.get("filePath")).getAsString();
            }
            if (!StringUtil.isEmpty(type) && !StringUtil.isEmpty(filePath)) {
                if ("css".equals(type)) {
                    result = String.format(CSS_SCRIPT, filePath + ".css?v=" + getVersion());
                } else if ("js".equals(type)) {
                    result = String.format(JS_SCRIPT, filePath + ".js?v=" + getVersion());
                }
            }
            env.getOut().write(result);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用程序启动时间做版本
     */
    private String getVersion() {
        String startUpTime = (String) Mvcs.getServletContextAttribute("start-up-time");
        if (StringUtil.isEmpty(startUpTime)) {
            String newVersionName = newVersionName();
            Mvcs.getServletContext().setAttribute("start-up-time", newVersionName);
            startUpTime = newVersionName;
        }
        return startUpTime;
    }

    private synchronized String newVersionName() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
}