package cn.limw.summer.cometd.websocket.server;

import org.cometd.server.BayeuxServerImpl;
import org.cometd.websocket.server.WebSocketTransport;

/**
 * @author li
 * @version 1 (2015年6月3日 下午1:56:47)
 * @since Java7
 */
public class AbstractWebSocketTransport extends WebSocketTransport {
    public AbstractWebSocketTransport(BayeuxServerImpl bayeux) {
        super(bayeux);
    }
}