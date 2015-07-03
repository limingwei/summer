package cn.limw.summer.open.weixin.event;

import cn.limw.summer.open.weixin.message.EventMessage;

/**
 * ScanEvent
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月19日 下午1:06:20)
 */
public class ScanEvent extends EventMessage {
    private static final long serialVersionUID = -2679553954049003210L;

    private String ticket;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getEvent() {
        return EventType.SCAN;
    }
}