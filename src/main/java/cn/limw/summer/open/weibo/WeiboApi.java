package cn.limw.summer.open.weibo;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import cn.limw.summer.http.HttpUtil;
import cn.limw.summer.open.ApiMeta;
import cn.limw.summer.util.Jsons;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Maps;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年7月17日 下午2:42:09)
 * @since Java7
 */
public class WeiboApi extends ApiMeta {
    private static final Logger log = Logs.slf4j();

    public WeiboApi() {}

    public WeiboApi(String appId, String appSecret, String redirectUri) {
        setAppId(appId);
        setAppSecret(appSecret);
        setRedirectUri(redirectUri);
    }

    public Map<String, Object> accessToken(String code) {
        String url = "https://api.weibo.com/oauth2/access_token?client_id=" + getAppId() + "&client_secret=" + getAppSecret() + "&grant_type=authorization_code&redirect_uri=" + getRedirectUri() + "&code=" + code;

        String source = HttpUtil.post(url).toString();
        if (StringUtil.isEmpty(source)) {
            return Maps.newMap();
        } else if (source.trim().startsWith("{")) {
            Map<String, Object> map = Jsons.toMap(source);
            return map; // accessToken.setToken((String) map.get("access_token"));   // accessToken.setUid((String) map.get("uid"));
        } else {
            throw new RuntimeException("accessToken 失败, code=" + code + ", url=" + url + ", response=" + source);
        }
    }

    public Map<String, Object> show(String uid) {
        final String url = "https://api.weibo.com/2/users/show.json?uid=" + uid + "&access_token=" + getToken();
        String response = HttpUtil.get(url).toString();
        log.debug("url={} response={}", url, response);
        try {
            return Jsons.toMap(response);
        } catch (Exception e) {
            log.error("" + e, e);
            return new HashMap<String, Object>();
        }
    }

    public Map<String, Object> getTokenInfo(String accessToken) {
        String response = HttpUtil.post("https://api.weibo.com/oauth2/get_token_info", Maps.toMap("access_token", accessToken)).getBody();
        return Jsons.toMap(response);
    }

    public String getUid(String accessToken) {
        String response = HttpUtil.get("https://api.weibo.com/2/account/get_uid.json?access_token=" + accessToken).getBody();
        Map map = Jsons.toMap(response);
        if (map.containsKey("uid")) {
            return "" + map.get("uid");
        } else if (map.containsKey("error")) {
            return "error:" + map.get("error");
        } else {
            log.error(response);
            return "";
        }
    }
}