package cn.limw.summer.open.weixin.message.handler;

import java.util.List;

import cn.limw.summer.open.weixin.message.Message;

/**
 * @author li
 * @version 1 (2015年6月18日 上午10:03:11)
 * @since Java7
 */
public class ComplexMessageHandler implements MessageHandler {
    private List<MessageHandler> messageHandlers;

    private MessageHandler defaultMessageHandler = AbstractMessageHandler.INSTANCE;

    public Boolean support(Message message) {
        return true;
    }

    public Boolean handle(Message message) {
        for (MessageHandler messageHandler : getMessageHandlers()) {
            if (messageHandler.support(message)) {
                return messageHandler.handle(message);
            }
        }
        return getDefaultMessageHandler().handle(message);
    }

    public List<MessageHandler> getMessageHandlers() {
        return messageHandlers;
    }

    public void setMessageHandlers(List<MessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers;
    }

    public MessageHandler getDefaultMessageHandler() {
        return defaultMessageHandler;
    }

    public void setDefaultMessageHandler(MessageHandler defaultMessageHandler) {
        this.defaultMessageHandler = defaultMessageHandler;
    }
}