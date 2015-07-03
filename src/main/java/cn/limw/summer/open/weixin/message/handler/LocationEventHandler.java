package cn.limw.summer.open.weixin.message.handler;

import cn.limw.summer.open.weixin.event.LocationEvent;
import cn.limw.summer.open.weixin.message.Message;

/**
 * @author li
 * @version 1 (2015年6月18日 上午10:24:12)
 * @since Java7
 */
public class LocationEventHandler extends EventMessageHandler {
    public Boolean support(Message message) {
        return message instanceof LocationEvent;
    }
}