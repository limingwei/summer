package cn.limw.summer.cometd.bayeux.server;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerTransport;
import org.cometd.server.BayeuxServerImpl;
import org.slf4j.Logger;

import cn.limw.summer.spring.web.context.AbstractServletContextAware;
import cn.limw.summer.util.Logs;

/**
 * BayeuxServer 构造器
 * @author li
 * @version 1 (2014年12月18日 上午10:16:51)
 * @since Java7
 */
public class CometDBayeuxServerInitializer extends AbstractServletContextAware {
    private static final Logger log = Logs.slf4j();

    private static BayeuxServerImpl bayeuxServer = null;

    /**
     * 初始化并返回 BayeuxServerImpl 实例
     */
    public synchronized BayeuxServer bayeuxServer() {
        if (null == bayeuxServer) {
            log.info("@Bean bayeuxServer() initing BayeuxServerImpl");
            BayeuxServerImpl server = new BayeuxServerImpl();
            configureBayeuxServer(server);
            getServletContext().setAttribute(BayeuxServer.ATTRIBUTE, server);
            return (bayeuxServer = server);
        }
        return bayeuxServer;
    }

    /**
     * 配置 BayeuxServerImpl
     */
    public void configureBayeuxServer(BayeuxServerImpl bayeuxServer) {
        ServletContext servletContext = getServletContext();
        log.info("configureBayeuxServer() servletContext={}", servletContext);

        bayeuxServer.setTransports(getTransports(bayeuxServer));
        bayeuxServer.setOption(ServletContext.class.getName(), servletContext);
        bayeuxServer.setOptions(getOptions());
    }

    /**
     * ServerTransport 列表
     */
    private ServerTransport[] getTransports(BayeuxServer bayeuxServer) {
        return new ServerTransport[0];
    }

    /**
     * Options 配置
     */
    public Map<String, Object> getOptions() {
        return new HashMap<String, Object>();
    }
}