package cn.limw.summer.open.weixin.message;

/**
 * ImageMessage
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月17日 下午8:00:47)
 */
public class VideoMessage extends MediaMessage {
    private static final long serialVersionUID = -5909656669519392879L;

    private String thumbMediaId;

    private String title;

    private String description;

    public String getType() {
        return MessageType.VIDEO;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) + "[id=" + getId() + ", type=" + getType() + ", from=" + getFrom() + ", to=" + getTo() // 
                + ", createTime=" + getCreateTime() + ", mediaId" + getMediaId() + ", thumbMediaId" + getThumbMediaId() + "]";
    }
}