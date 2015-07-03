package cn.limw.summer.open.weixin.message.handler;

import cn.limw.summer.open.weixin.message.Message;
import cn.limw.summer.open.weixin.message.NewsMessage;

/**
 * @author li
 * @version 1 (2015年6月18日 上午10:25:26)
 * @since Java7
 */
public class NewsMessageHandler extends AbstractMessageHandler {
    public Boolean support(Message message) {
        return message instanceof NewsMessage;
    }
}