package cn.limw.summer.web.wro4j.freemarker;

import java.io.IOException;
import java.util.Map;

import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.util.StringUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @author li
 * @version 1 (2015年3月30日 下午7:21:39)
 * @since Java7
 */
public class SmartImportResourceDirective extends DynamicImportResourceDirective {
    private Boolean wroOn = true;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String wroOn = get_wro_on();

        if ("false".equalsIgnoreCase(wroOn)) {
            dynamicImportResourceDirectiveExecute(env, params, loopVars, body); // 不压缩合并
        } else {
            dynamicImportJsOrCssDirectiveExecute(env, params, loopVars, body); // 压缩合并
        }
    }

    public String get_wro_on() {
        String wro_on_in_header = Mvcs.getRequestHeader("wro_on");
        if (!StringUtil.isEmpty(wro_on_in_header)) {
            return wro_on_in_header;
        } else {
            Object wro_on_in_session = Mvcs.getSession("wro_on");
            if (null != wro_on_in_session) {
                return wro_on_in_session + "";
            } else {
                return getWroOn() + "";
            }
        }
    }

    public Boolean getWroOn() {
        return wroOn;
    }

    public void setWroOn(Boolean wroOn) {
        this.wroOn = wroOn;
    }
}