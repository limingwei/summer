package cn.limw.summer.yui.compressor.config;

import java.io.InputStream;
import java.util.List;

import cn.limw.summer.util.Files;
import cn.limw.summer.yui.compressor.WroYuiUtil;

/**
 * @author li
 * @version 1 (2015年6月26日 下午6:19:48)
 * @since Java7
 */
public class WroXmlYuiCompressorConfig implements YuiCompressorConfig {
    public static final String UTF_8 = "UTF-8";

    public WroXmlYuiCompressorConfig(InputStream inputStream) {
        String config = Files.toString(WroYuiUtil.class.getResourceAsStream("/wro.xml"), UTF_8);
    }

    public List<YuiCompressorGroup> getYuiCompressorGroups() {
        return null;
    }
}