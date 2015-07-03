package cn.limw.summer.open.weixin.message.handler;

import cn.limw.summer.open.weixin.message.Message;

/**
 * 消息处理器
 * @author li
 * @version 1 (2015年6月18日 上午10:02:11)
 * @since Java7
 */
public interface MessageHandler {
    /**
     * 是否可以处理指定消息
     */
    public Boolean support(Message message);

    /**
     * 处理消息
     */
    public Boolean handle(Message message);
}