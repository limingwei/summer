package cn.limw.summer.open.push.baidu;

import java.util.Map;

import org.slf4j.Logger;

import cn.limw.summer.http.HttpUtil;
import cn.limw.summer.open.ApiMeta;
import cn.limw.summer.util.Logs;

/**
 * @author lgb
 * @version 1 (2014年9月23日上午11:01:35)
 * @since Java7
 */
public class BaiduPushApiImpl extends ApiMeta implements BaiduPushApi {
    private static final Logger log = Logs.slf4j();

    public BaiduPushApiImpl() {}

    public BaiduPushApiImpl(String appKey, String appSecret) {
        setAppKey(appKey);
        setAppSecret(appSecret);
    }

    public String push(BaiduPushMessage message) {
        String url = "http://channel.api.duapp.com/rest/2.0/channel/channel";

        Map<String, String> parameters = message.toMap();
        parameters.put("apikey", getAppKey());
        parameters.put("timestamp", (System.currentTimeMillis() / 1000L) + "");
        parameters.put("sign", BaiduPushUtil.digest("POST", url, getAppSecret(), parameters));

        String response = HttpUtil.post(url, BaiduPushUtil.buildParameters(parameters).getBytes()).toString();

        log.info("parameters={}, response={}", parameters, response);
        return response;
    }
}