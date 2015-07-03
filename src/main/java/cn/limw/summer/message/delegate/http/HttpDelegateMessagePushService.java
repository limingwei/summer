package cn.limw.summer.message.delegate.http;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import cn.limw.summer.http.Request;
import cn.limw.summer.http.Response;
import cn.limw.summer.java.collection.NiceToStringMap;
import cn.limw.summer.message.MessagePushService;
import cn.limw.summer.spring.web.interceptor.signcheck.SignCheckUtil;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Base64Util;
import cn.limw.summer.util.Jsons;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年2月10日 下午5:17:21)
 * @since Java7
 */
public class HttpDelegateMessagePushService implements MessagePushService {
    private static final Logger log = Logs.slf4j();

    private String url;

    private String signSecret;

    public void publish(String channel, Object data, String... sessionIds) {
        try {
            Map<String, Object> fields = new NiceToStringMap(new HashMap<String, Object>());
            fields.put("channel", channel);

            String json = dataToJson(data);

            fields.put("data", json);
            fields.put("sessionIds", Jsons.toJson(sessionIds));

            String sign = SignCheckUtil.sign(fields, getSignSecret());
            fields.put("_sign", sign);

            Request request = new Request();
            request.setUrl(Asserts.noEmpty(getUrl(), "url不可以为空"));
            request.setMethod(Request.POST);
            request.setFields(fields);
            Response response = request.execute();

            String body = response.getBody();
            log.info("send cometd by http request channel={}, sign={}, response is {}", channel, sign, body);
            if (body.equals("true")) {
                // 成功
            } else {
                RuntimeException error = new RuntimeException("HttpDelegateMessagePushService.publish() error, url=" + getUrl() + ", signSecret=" + getSignSecret() + ", fields=" + fields + ", body=" + body);
                log.error("HttpDelegateMessagePushService.publish 失败", error);
            }
        } catch (Exception e) {
            RuntimeException error = new RuntimeException("HttpDelegateMessagePushService.publish() failed , " + e.getMessage(), e);
            log.error("HttpDelegateMessagePushService.publish 异常", error);
        }
    }

    public String dataToJson(Object data) {
        String json = Jsons.toJson(data);

        json = Base64Util.urlWellEncrypt(json);
        return json;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSignSecret() {
        return signSecret;
    }

    public void setSignSecret(String signSecret) {
        this.signSecret = signSecret;
    }
}