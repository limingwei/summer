package cn.limw.summer.message;

/**
 * @author li
 * @version 1 (2014年7月10日 下午4:04:53)
 * @since Java7
 */
public interface MessagePushService {
    /**
     * 发送消息
     * @param channel 目标通道
     * @param data 消息数据
     * @param sessionIds 目标用户, 为空时为所有
     */
    public void publish(String channel, Object data, String... sessionIds);
}