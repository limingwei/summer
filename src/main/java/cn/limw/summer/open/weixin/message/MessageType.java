package cn.limw.summer.open.weixin.message;

/**
 * MessageType
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月17日 下午7:53:16)
 */
public interface MessageType {
    public static final String TEXT = "text";

    public static final String IMAGE = "image";

    public static final String VOICE = "voice";

    public static final String VIDEO = "video";

    public static final String SHORT_VIDEO = "shortvideo";

    public static final String LOCATION = "location";

    public static final String LINK = "link";

    public static final String EVENT = "event";

    public static final String NEWS = "news";

    public static final String[] TYPE_FILES = { IMAGE, VOICE, VIDEO, SHORT_VIDEO };

    public static final String[] TYPE_VIDEOS = { VIDEO, SHORT_VIDEO };
}