package cn.limw.summer.open.push.baidu;

import cn.limw.summer.open.push.baidu.BaiduPushApiImpl;
import cn.limw.summer.open.push.baidu.BaiduPushMessage;
import cn.limw.summer.open.push.baidu.MessageType;
import cn.limw.summer.open.push.baidu.Method;
import cn.limw.summer.open.push.baidu.PushType;
import cn.limw.summer.time.Clock;

/**
 * @author li
 * @version 1 (2014年11月26日 下午4:22:15)
 * @since Java7
 */
public class BaiduPushApiTest {
    private static final String APP_KEY = "fYnkawt5LTdbHFd5qqC0hpO8";

    private static final String SECRET_KEY = "xKY5Vx9G185n26egRvZNrurtH1QCjmB2";

    public void pushToAll() {
        BaiduPushApiImpl baiduPushApi = new BaiduPushApiImpl(APP_KEY, SECRET_KEY);

        BaiduPushMessage message = new BaiduPushMessage();
        message.setMethod(Method.PUSH_MSG);
        message.setPushType(PushType.ALL);
        message.setMessageType(MessageType.NOTICE);
        message.setKey("channel_msg_key");
        message.setBody("{\"title\":\" BaiduPushApi 广播消息\",\"description\":\" BaiduPushApi 广播消息描述 " + Clock.now().toYYYY_MM_DD_HH_MM_SS() + "\"}");

        System.err.println(baiduPushApi.push(message));
    }

    public void pushToOne() {
        BaiduPushApiImpl baiduPushApi = new BaiduPushApiImpl(APP_KEY, SECRET_KEY);

        BaiduPushMessage message = new BaiduPushMessage();
        message.setMethod(Method.PUSH_MSG);
        message.setPushType(PushType.ONE);
        message.setMessageType(MessageType.NEWS);
        message.setKey("channel_msg_key");
        message.setBody("{\"title\":\" BaiduPushApi 广播消息\",\"description\":\" BaiduPushApi 广播消息描述 " + Clock.now().toYYYY_MM_DD_HH_MM_SS() + "\"}");
        message.setUserId("963628788211970948");

        System.err.println(baiduPushApi.push(message));
    }
}