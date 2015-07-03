package cn.limw.summer.open.weixin.message.handler;

import cn.limw.summer.open.weixin.event.ClickEvent;
import cn.limw.summer.open.weixin.message.Message;

/**
 * @author li
 * @version 1 (2015年6月18日 上午10:22:44)
 * @since Java7
 */
public class ClickEventHandler extends EventMessageHandler {
    public Boolean support(Message message) {
        return message instanceof ClickEvent;
    }
}