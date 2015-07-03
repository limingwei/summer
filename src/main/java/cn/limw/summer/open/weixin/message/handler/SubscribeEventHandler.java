package cn.limw.summer.open.weixin.message.handler;

import cn.limw.summer.open.weixin.event.SubscribeEvent;
import cn.limw.summer.open.weixin.message.Message;

/**
 * @author li
 * @version 1 (2015年6月18日 上午10:21:36)
 * @since Java7
 */
public class SubscribeEventHandler extends EventMessageHandler {
    public Boolean support(Message message) {
        return message instanceof SubscribeEvent;
    }
}