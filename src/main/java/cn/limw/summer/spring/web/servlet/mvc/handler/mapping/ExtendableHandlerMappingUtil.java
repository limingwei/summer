package cn.limw.summer.spring.web.servlet.mvc.handler.mapping;

import java.util.Map;

import cn.limw.summer.freemarker.util.FreeMarkerUtil;

/**
 * @author li
 * @version 1 (2015年1月22日 下午3:56:58)
 * @since Java7
 */
public class ExtendableHandlerMappingUtil {
    protected static String[] mergeValues(String[] patterns, Map<String, Object> map) {
        String[] patternsMerged = new String[patterns.length];
        for (int i = 0; i < patterns.length; i++) {
            patternsMerged[i] = FreeMarkerUtil.merge(patterns[i].replace('@', '$'), map);
        }
        return patternsMerged;
    }
}