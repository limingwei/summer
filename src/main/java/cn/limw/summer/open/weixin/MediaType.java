package cn.limw.summer.open.weixin;

/**
 * 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
 * @author li
 * @version 1 (2015年6月3日 上午10:29:59)
 * @since Java7
 */
public interface MediaType {
    public static final String IMAGE = "image";

    public static final String VOICE = "voice";

    public static final String VIDEO = "video";

    public static final String THUMB = "thumb";
}