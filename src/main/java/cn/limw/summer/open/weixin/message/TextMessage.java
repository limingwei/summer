package cn.limw.summer.open.weixin.message;

/**
 * Message
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月17日 下午7:45:39)
 */
public class TextMessage extends Message {
    private static final long serialVersionUID = -4560242388181481679L;

    private String content;

    public TextMessage() {}

    public TextMessage(String to, String content) {
        setTo(to);
        setContent(content);
    }

    public String getType() {
        return MessageType.TEXT;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) + "[id=" + getId() + ", type=" + getType() + ", from=" + getFrom() + ", to=" + getTo() // 
                + ", createTime=" + getCreateTime() + ", content=" + getContent() + "]";
    }
}