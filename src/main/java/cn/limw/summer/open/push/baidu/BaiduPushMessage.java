package cn.limw.summer.open.push.baidu;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年11月27日 上午11:05:36)
 * @since Java7
 */
public class BaiduPushMessage implements Serializable {
    private static final long serialVersionUID = -6321289352754250206L;

    /**
     * 设备默认Android
     */
    private Integer deviceType = DeviceType.ANDRIOD;

    /**
     * 类型默认消息
     */
    private Integer messageType = MessageType.NEWS;

    private String body;

    private String method;

    private String key;

    private Integer pushType;

    private String userId;

    private String channelId;

    public BaiduPushMessage() {}

    public BaiduPushMessage(String method, Integer pushType, Integer messageType) {
        setMethod(method);
        setPushType(pushType);
        setMessageType(messageType);
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPushType() {
        return pushType;
    }

    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

    public String getKey() {
        return key;
    }

    /**
     * 消息标识, 指定消息标识，必须和messages一一对应, 相同消息标识的消息会自动覆盖
     */
    public void setKey(String key) {
        this.key = key;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("messages", Asserts.noEmpty(getBody(), "messages 不可以为空"));//
        map.put("device_type", Asserts.noNull(getDeviceType()) + "");
        map.put("message_type", Asserts.noNull(getMessageType()) + "");
        map.put("method", Asserts.noEmpty(getMethod(), "请指定 message.method"));
        map.put("msg_keys", getKey());
        map.put("push_type", Asserts.noNull(getPushType()) + "");

        if (!StringUtil.isEmpty(getUserId())) {
            map.put("user_id", getUserId());
        }
        if (!StringUtil.isEmpty(getChannelId())) {
            map.put("channel_id", getChannelId());
        }
        return map;
    }
}