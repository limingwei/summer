package cn.limw.summer.open.weixin.message.handler;

import cn.limw.summer.open.weixin.message.Message;
import cn.limw.summer.open.weixin.message.VoiceMessage;

/**
 * @author li
 * @version 1 (2015年6月18日 上午10:15:55)
 * @since Java7
 */
public class VoiceMessageHandler extends MediaMessageHandler {
    public Boolean support(Message message) {
        return message instanceof VoiceMessage;
    }
}