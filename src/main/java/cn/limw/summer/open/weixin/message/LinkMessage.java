package cn.limw.summer.open.weixin.message;

/**
 * ImageMessage
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月17日 下午8:00:47)
 */
public class LinkMessage extends Message {
    private static final long serialVersionUID = -1510707784831259318L;

    private String title;

    private String description;

    private String url;

    public String getType() {
        return MessageType.LINK;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) + "[id=" + getId() + ", type=" + getType() + ", from=" + getFrom() + ", to=" + getTo() // 
                + ", createTime=" + getCreateTime() + ", title" + getTitle() + ", url" + getUrl() + ", description" + getDescription() + "]";
    }
}