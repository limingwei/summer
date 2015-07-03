package cn.limw.summer.open.weixin.message;

/**
 * VoiceMessage
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月17日 下午8:00:47)
 */
public class VoiceMessage extends MediaMessage {
    private static final long serialVersionUID = -8063058819582658552L;

    private String format;

    private String recognition;

    public String getType() {
        return MessageType.VOICE;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) + "[id=" + getId() + ", type=" + getType() + ", from=" + getFrom() + ", to=" + getTo() // 
                + ", createTime=" + getCreateTime() + ", mediaId" + getMediaId() + ", format" + getFormat() + ", recognition" + getRecognition() + "]";
    }
}