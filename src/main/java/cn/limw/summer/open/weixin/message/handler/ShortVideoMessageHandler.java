package cn.limw.summer.open.weixin.message.handler;

import cn.limw.summer.open.weixin.message.Message;
import cn.limw.summer.open.weixin.message.ShortVideoMessage;

/**
 * @author li
 * @version 1 (2015年6月18日 上午10:22:01)
 * @since Java7
 */
public class ShortVideoMessageHandler extends MediaMessageHandler {
    public Boolean support(Message message) {
        return message instanceof ShortVideoMessage;
    }
}