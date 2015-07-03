package cn.limw.summer.open.weixin.message.handler;

import org.slf4j.Logger;

import cn.limw.summer.open.weixin.message.Message;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年6月18日 上午10:19:35)
 * @since Java7
 */
public class AbstractMessageHandler implements MessageHandler {
    private static final Logger log = Logs.slf4j();

    public static final MessageHandler INSTANCE = new AbstractMessageHandler();

    public Boolean support(Message message) {
        return true;
    }

    public Boolean handle(Message message) {
        log.info("handle() message=" + message);
        return false;
    }
}