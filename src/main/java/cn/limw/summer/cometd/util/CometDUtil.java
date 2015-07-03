package cn.limw.summer.cometd.util;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.slf4j.Logger;

import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年12月16日 下午2:08:57)
 * @since Java7
 */
public class CometDUtil {
    private static final Logger log = Logs.slf4j();

    /**
     * 发消息到指定channel
     */
    public static void publishToChannel(String channel, Object data, ServerSession serverSession) {
        log.info("publishToChannel() channel={} session.id={}", channel, serverSession.getId());
        serverSession.getLocalSession().getChannel(channel).publish(data);
    }

    /**
     * 发送消息到指定session
     */
    public static void publishToSession(String channel, Object data, String sessionId, BayeuxServer bayeuxServer, ServerSession serverSession) {
        if (StringUtil.isEmpty(sessionId)) {
            log.info("publishToSession() error sessionId is null, channel={}", channel);
        } else {
            ServerSession remote = bayeuxServer.getSession(sessionId);
            if (null == remote) {
                log.info("publishToSession() error, coule not find remote ServerSession sessionId={}", sessionId);
            } else {
                ServerMessage.Mutable forward = bayeuxServer.newMessage();
                forward.setChannel(channel);
                forward.setData(data);
                remote.deliver(serverSession, forward);

                log.info("publishToSession() success channel={}, sessionId={}", channel, sessionId);
            }
        }
    }

    public static void publishToSessions(String channel, Object data, String[] sessionIds, BayeuxServer bayeuxServer, ServerSession serverSession) {
        for (String sessionId : sessionIds) {
            publishToSession(channel, data, sessionId, bayeuxServer, serverSession);
        }
    }
}