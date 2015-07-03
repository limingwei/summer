package cn.limw.summer.yui.compressor.config;

import java.io.Serializable;
import java.util.List;

/**
 * @author li
 * @version 1 (2015年6月26日 下午6:26:07)
 * @since Java7
 */
public class YuiCompressorGroup implements Serializable {
    private static final long serialVersionUID = -305369513599258575L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getJavascriptSources() {
        return null;
    }

    public List<String> getCssSources() {
        return null;
    }
}