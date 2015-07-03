package cn.limw.summer.open.weixin.message.handler;

import cn.limw.summer.open.weixin.message.ImageMessage;
import cn.limw.summer.open.weixin.message.Message;

/**
 * @author li
 * @version 1 (2015年6月18日 上午10:26:30)
 * @since Java7
 */
public class ImageMessageHandler extends MediaMessageHandler {
    public Boolean support(Message message) {
        return message instanceof ImageMessage;
    }
}