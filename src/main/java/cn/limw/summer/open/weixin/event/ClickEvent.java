package cn.limw.summer.open.weixin.event;

import cn.limw.summer.open.weixin.message.EventMessage;

/**
 * ClickEvent
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月18日 下午5:22:29)
 */
public class ClickEvent extends EventMessage {
    private static final long serialVersionUID = 3310546545697901657L;

    private String eventKey;

    public String getEvent() {
        return EventType.CLICK;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String toString() {
        return "<xml>"//
                + "<ToUserName><![CDATA[" + this.getTo() + "]]></ToUserName>"//
                + "<FromUserName><![CDATA[" + this.getFrom() + "]]></FromUserName>"//
                + "<CreateTime>" + this.getCreateTime() + "</CreateTime>"//
                + "<MsgType><![CDATA[" + this.getType() + "]]></MsgType>"//
                + "<Event><![CDATA[" + this.getEvent() + "]]></Event>"//
                + "<EventKey><![CDATA[" + this.getEventKey() + "]]></EventKey>"//
                + "</xml>";
    }
}