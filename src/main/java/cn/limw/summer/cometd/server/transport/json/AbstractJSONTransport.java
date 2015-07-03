package cn.limw.summer.cometd.server.transport.json;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerMessage.Mutable;
import org.cometd.server.BayeuxServerImpl;
import org.cometd.server.ServerSessionImpl;
import org.cometd.server.transport.JSONTransport;

import cn.limw.summer.cometd.server.transport.util.StreamHttpTransportUtil;

/**
 * @author li
 * @version 1 (2015年6月12日 下午2:14:51)
 * @since Java7
 */
public class AbstractJSONTransport extends JSONTransport {
    public AbstractJSONTransport(BayeuxServerImpl bayeux) {
        super(bayeux);
    }

    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LongPollScheduler scheduler = (LongPollScheduler) request.getAttribute(StreamHttpTransportUtil.SCHEDULER_ATTRIBUTE);
        if (scheduler == null) { // Not a resumed /meta/connect, process messages.
            try {
                ServerMessage.Mutable[] messages = parseMessages(request);
                processMessages(request, response, messages);
            } catch (ParseException x) {
                handleJSONParseException(request, response, x.getMessage(), x.getCause());
            }
        } else {
            doResume(request, scheduler.getServerSession(), scheduler.getMetaConnectReply());
        }
    }

    public void doResume(HttpServletRequest request, ServerSessionImpl serverSession, Mutable metaConnectReply) {
        callSuperResume(request.getAsyncContext(), serverSession, metaConnectReply);
    }

    public void callSuperResume(AsyncContext asyncContext, ServerSessionImpl serverSession, Mutable metaConnectReply) {
        resume(asyncContext, serverSession, metaConnectReply);
    }
}