package cn.limw.summer.open.weixin.message;

/**
 * MediaMessage
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月17日 下午8:19:57)
 */
public abstract class MediaMessage extends Message {
    private static final long serialVersionUID = -4207489280413325364L;

    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}