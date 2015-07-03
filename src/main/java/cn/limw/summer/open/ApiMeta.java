package cn.limw.summer.open;

/**
 * @author li
 * @version 1 (2014年9月5日 下午5:46:25)
 * @since Java7
 */
public class ApiMeta {
    private String appId;

    private String appKey;

    private String appSecret;

    private String redirectUri;

    private String token;

    private String url;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String openSite) {
        this.url = openSite;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}