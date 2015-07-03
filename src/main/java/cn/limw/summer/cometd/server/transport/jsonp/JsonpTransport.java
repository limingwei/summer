package cn.limw.summer.cometd.server.transport.jsonp;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;

import org.cometd.bayeux.server.ServerMessage.Mutable;
import org.cometd.server.BayeuxServerImpl;
import org.cometd.server.ServerSessionImpl;
import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年6月12日 下午2:16:48)
 * @since Java7
 */
public class JsonpTransport extends AbstractJSONPTransport {
    private static final Logger log = Logs.slf4j();

    public JsonpTransport(BayeuxServerImpl bayeux) {
        super(bayeux);
    }

    public void doResume(HttpServletRequest request, ServerSessionImpl serverSession, Mutable metaConnectReply) {
        try {
            AsyncContext asyncContext = request.getAsyncContext();
            callSuperResume(asyncContext, serverSession, metaConnectReply);
        } catch (IllegalStateException e) {
            log.error("doResume error " + e);
        }
    }
}