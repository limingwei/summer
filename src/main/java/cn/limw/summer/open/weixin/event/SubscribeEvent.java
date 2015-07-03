package cn.limw.summer.open.weixin.event;

import cn.limw.summer.open.weixin.message.EventMessage;

/**
 * SubscribeEvent
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月17日 下午8:00:47)
 */
public class SubscribeEvent extends EventMessage {
    private static final long serialVersionUID = -4227984716683107100L;

    private String ticket;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getEvent() {
        return EventType.SUBSCRIBE;
    }
}