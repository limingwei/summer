package cn.limw.summer.open.weixin.event;

/**
 * EventType
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月17日 下午8:24:12)
 */
public interface EventType {
    public static final String SUBSCRIBE = "subscribe";

    public static final String CLICK = "click";

    public static final String UNSUBSCRIBE = "unsubscribe";

    public static final String SCAN = "scan";

    public static final String LOCATION = "location";
}