package cn.limw.summer.open.weixin.event;

import cn.limw.summer.open.weixin.message.EventMessage;

/**
 * SubscribeEvent
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月17日 下午8:00:47)
 */
public class UnSubscribeEvent extends EventMessage {
    private static final long serialVersionUID = -7388142190651048585L;

    public String getEvent() {
        return EventType.UNSUBSCRIBE;
    }
}