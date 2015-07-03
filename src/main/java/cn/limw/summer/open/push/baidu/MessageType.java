package cn.limw.summer.open.push.baidu;

/**
 * @author li
 * @version 1 (2014年11月27日 下午2:55:21)
 * @since Java7
 */
public interface MessageType {
    /**
     * 消息类型, 0：消息（透传给应用的消息体）
     */
    Integer NEWS = 0;

    /**
     * 消息类型, 1：通知（对应设备上的消息通知）, 会出现在顶栏的
     */
    Integer NOTICE = 1;
}