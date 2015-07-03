package cn.limw.summer.open.weixin.message.handler;

import cn.limw.summer.open.weixin.message.MediaMessage;
import cn.limw.summer.open.weixin.message.Message;

/**
 * @author li
 * @version 1 (2015年6月18日 上午10:16:13)
 * @since Java7
 */
public class MediaMessageHandler extends AbstractMessageHandler {
    public Boolean support(Message message) {
        return message instanceof MediaMessage;
    }
}