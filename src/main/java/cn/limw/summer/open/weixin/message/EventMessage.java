package cn.limw.summer.open.weixin.message;

/**
 * ImageMessage
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月17日 下午8:00:47)
 */
public abstract class EventMessage extends Message {
    private static final long serialVersionUID = -4316965780988840960L;

    private String event;

    private String eventKey;

    public String getType() {
        return MessageType.EVENT;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) + "[id=" + getId() + ", type=" + getType() + ", from=" + getFrom() + ", to=" + getTo() // 
                + ", createTime=" + getCreateTime() + ", event" + getEvent() + ", eventKey" + getEventKey() + "]";
    }
}