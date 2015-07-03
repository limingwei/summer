package cn.limw.summer.open.weixin.message;

/**
 * @author li
 * @version 1 (2015年5月29日 下午5:31:54)
 * @since Java7
 */
public class ShortVideoMessage extends VideoMessage {
    private static final long serialVersionUID = -6475912790878052000L;

    public String getType() {
        return MessageType.SHORT_VIDEO;
    }
}