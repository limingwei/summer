package cn.limw.summer.open.push.apple;

import java.io.Serializable;
import java.util.Map;

import cn.limw.summer.util.Maps;

/**
 * @author li
 * @version 1 (2014年12月5日 上午11:08:10)
 * @since Java7
 */
public class ApplePushMessage implements Serializable {
    private static final long serialVersionUID = -3419765327193379644L;

    private String body;

    private String deviceId;

    public String getBody() {
        return body;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Map<String, Object> toMap() {
        return Maps.toMap("body", getBody(), "deviceId", getDeviceId());
    }
}