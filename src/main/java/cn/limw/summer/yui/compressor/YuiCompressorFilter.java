package cn.limw.summer.yui.compressor;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import cn.limw.summer.web.filter.AbstractFilter;

/**
 * @author li
 * @version 1 (2015年6月25日 下午1:22:58)
 * @since Java7
 */
public class YuiCompressorFilter extends AbstractFilter {
    public void init(FilterConfig filterConfig) throws ServletException {
        WroYuiUtil.compressResources(filterConfig.getServletContext());
    }
}