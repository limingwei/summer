package cn.limw.summer.open.weixin.message.handler;

import cn.limw.summer.open.weixin.event.UnSubscribeEvent;
import cn.limw.summer.open.weixin.message.Message;

/**
 * @author li
 * @version 1 (2015年6月18日 上午10:18:14)
 * @since Java7
 */
public class UnSubscribeEventHandler extends EventMessageHandler {
    public Boolean support(Message message) {
        return message instanceof UnSubscribeEvent;
    }
}