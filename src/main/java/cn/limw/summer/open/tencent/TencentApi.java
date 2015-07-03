package cn.limw.summer.open.tencent;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import cn.limw.summer.http.HttpUtil;
import cn.limw.summer.open.ApiMeta;
import cn.limw.summer.util.Jsons;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年7月18日 上午10:13:20)
 * @since Java7
 */
public class TencentApi extends ApiMeta {
    private static final Logger log = Logs.slf4j();

    public TencentApi() {}

    public TencentApi(String appId, String appSecret, String redirectUri) {
        setAppId(appId);
        setAppSecret(appSecret);
        setRedirectUri(redirectUri);
    }

    public Map<String, Object> token(String code) {
        String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_secret=" + getAppSecret() + "&client_id=" + getAppId() + "&redirect_uri=" + getRedirectUri() + "&code=" + code;
        String source = HttpUtil.get(url).toString();
        String[] params = source.split("&");
        Map<String, Object> map = new HashMap<String, Object>();
        for (String param : params) {
            if (!StringUtil.isEmpty(param)) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    map.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return map;
    }

    public Map<String, Object> me() {
        String response = HttpUtil.get("https://graph.qq.com/oauth2.0/me?access_token=" + getToken()).toString();
        log.info("me() app_id={}, app_secret={}, token={}, response={}", getAppId(), getAppSecret(), getToken(), response);
        response = response.replace("callback(", "").replace(");", "");
        return Jsons.toMap(response);
    }

    public Map<String, Object> getUserInfo(String openId) {
        String url = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=" + getAppId()//
                + "&access_token=" + getToken() + "&openid=" + openId + "&format=json";
        String response = HttpUtil.get(url).toString();
        log.info("getUserInfo() app_id={}, app_secret={}, access_token={}, openId={}, response={}", getAppId(), getAppSecret(), getToken(), openId, response);
        Map<String, Object> map = Jsons.toMap(response);
        return map;
    }
}