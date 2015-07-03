package cn.limw.summer.open.weixin.message;

import java.io.Serializable;

/**
 * Message
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月17日 下午7:45:39)
 */
public abstract class Message implements Serializable {
    private static final long serialVersionUID = -1097375671439794324L;

    public Message() {
        this.createTime = (System.currentTimeMillis() * 1000);
    }

    private String id;

    private String from;

    private String to;

    private Long createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public abstract String getType();

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) + "[id=" + getId() + ", type=" + getType() + ", from=" + getFrom() + ", to=" + getTo() + ", createTime=" + getCreateTime() + "]";
    }
}