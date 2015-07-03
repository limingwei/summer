package cn.limw.summer.yui.compressor;

import javax.servlet.ServletContextEvent;

import cn.limw.summer.javax.servlet.AbstractServletContextListener;

/**
 * @author li
 * @version 1 (2015年6月25日 下午1:22:58)
 * @since Java7
 */
public class YuiCompressorServletContextListener extends AbstractServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        WroYuiUtil.compressResources(servletContextEvent.getServletContext());
    }
}