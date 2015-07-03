package cn.limw.summer.open.weixin.message.handler;

import cn.limw.summer.open.weixin.message.Message;
import cn.limw.summer.open.weixin.message.TextMessage;

/**
 * @author li
 * @version 1 (2015年6月18日 上午10:21:27)
 * @since Java7
 */
public class TextMessageHandler extends AbstractMessageHandler {
    public Boolean support(Message message) {
        return message instanceof TextMessage;
    }
}