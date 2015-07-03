package cn.limw.summer.cometd.websocket.server;

import javax.servlet.ServletContext;
import javax.websocket.server.ServerContainer;

import org.cometd.server.BayeuxServerImpl;
import org.slf4j.Logger;
import org.springframework.mock.web.MockServletContext;

import cn.limw.summer.javax.websocket.server.VoidServerContainer;
import cn.limw.summer.spring.web.servlet.Mvcs;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年2月11日 上午11:48:40)
 * @since Java7
 */
public class WebSocketTransport extends AbstractWebSocketTransport {
    private static final Logger log = Logs.slf4j();

    public WebSocketTransport(BayeuxServerImpl bayeux) {
        super(bayeux);
    }

    public void init() {
        ServletContext servletContext = (ServletContext) getOption(ServletContext.class.getName());

        ServerContainer serverContainer = (ServerContainer) servletContext.getAttribute(ServerContainer.class.getName());
        if (serverContainer == null) {
            if (servletContext instanceof MockServletContext) {
                log.info("serverContainer is null, servletContext is MockServletContext");
                servletContext.setAttribute(ServerContainer.class.getName(), serverContainer = VoidServerContainer.INSTANCE);
            } else {
                log.info("serverContainer is null, servletContext={}, servletContextAttributes={}", servletContext, Mvcs.getServletContextAttributes(servletContext));
                throw new IllegalArgumentException("Missing WebSocket ServerContainer, servletContext=" + servletContext + ", servletContextAttributes=" + Mvcs.getServletContextAttributes(servletContext));
            }
        } else {
            log.info("serverContainer={}", serverContainer);
        }

        super.init();
    }
}