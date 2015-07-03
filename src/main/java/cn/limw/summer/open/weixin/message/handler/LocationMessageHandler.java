package cn.limw.summer.open.weixin.message.handler;

import cn.limw.summer.open.weixin.message.LocationMessage;
import cn.limw.summer.open.weixin.message.Message;

/**
 * @author li
 * @version 1 (2015年6月18日 上午10:22:29)
 * @since Java7
 */
public class LocationMessageHandler extends AbstractMessageHandler {
    public Boolean support(Message message) {
        return message instanceof LocationMessage;
    }
}