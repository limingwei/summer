package cn.limw.summer.open.weixin.api;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;

import cn.limw.summer.http.HttpUtil;
import cn.limw.summer.http.Request;
import cn.limw.summer.http.Response;
import cn.limw.summer.open.weixin.message.Message;
import cn.limw.summer.open.weixin.util.WeixinUtil;
import cn.limw.summer.util.Jsons;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年5月28日 上午10:58:15)
 * @since Java7
 */
public class WeixinApi implements Serializable {
    private static final long serialVersionUID = -7050788217483471499L;

    private static final Logger log = Logs.slf4j();

    private static final String LANG_ZH_CN = "zh_CN";

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * 获取微信Token
     */
    @SuppressWarnings("unchecked")
    public String getWeixinToken(String appId, String appSecret) {
        Request request = new Request();
        request.setUrl("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret);
        request.setMethod(Request.GET);
        Response response = request.execute();
        String result = response.toString();
        Map<String, Object> map = Jsons.toMap(result);
        return this.accessToken = StringUtil.toString(map.get("access_token"));
    }

    public String sendMessage(Message message) {
        if (null == message) {
            log.error("message is null");
            return "message is null";
        } else {
            Request request = new Request();
            request.setUrl("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + getAccessToken());
            request.setMethod(Request.POST);
            request.setData(WeixinUtil.messageToJson(message).getBytes());
            Response response = request.execute();
            return response.toString();
        }
    }

    public String createMenu(String menusToString) {
        Request request = new Request();
        request.setUrl("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + getAccessToken());
        request.setMethod(Request.POST);
        request.setData(menusToString.getBytes());
        Response response = request.execute();
        return response.toString();
    }

    public String getMenu() {
        Request request = new Request();
        request.setUrl("https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + getAccessToken());
        request.setMethod(Request.GET);
        Response response = request.execute();
        return response.toString();
    }

    public String deleteMenu() {
        Request request = new Request();
        request.setUrl("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + getAccessToken());
        request.setMethod(Request.GET);
        Response response = request.execute();
        return response.toString();
    }

    /**
     * 上传资源
     * @param type image voice video thumb
     * @param media File URL InputStream byte[]
     * @return 返回 {"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789}
     */
    public WeixinMediaUploadResult uploadMedia(String type, Object media) {
        Request request = new Request();
        request.setMethod(Request.POST);
        request.setUrl("http://file.api.weixin.qq.com/cgi-bin/media/upload");
        request.setField("access_token", getAccessToken());
        request.setField("type", type);
        request.setField("media", media);
        Response response = request.execute();
        WeixinMediaUploadResult weixinMediaUploadResult = new WeixinMediaUploadResult(response.toString());
        if (!weixinMediaUploadResult.getSuccess()) {
            log.error("uploadMedia failed, response={}, request={}", response, request);
        }
        return weixinMediaUploadResult;
    }

    /**
     * 微信是跳转
     * @return 返回的是跳转地址
     */
    public String getMedia(String mediaId) {
        return "https://api.weixin.qq.com/cgi-bin/media/get?access_token=" + getAccessToken() + "&media_id=" + mediaId;
    }

    /**
     * 微信是跳转
     * @return 返回的文件内容
     */
    public InputStream downloadMedia(String mediaId) {
        try {
            return new URL(getMedia(mediaId)).openStream();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取用户基本信息
     * http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
     * @param openId
     */
    public Map<String, Object> getUserInfo(String openId) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + getAccessToken() + "&openid=" + openId + "&lang=" + LANG_ZH_CN;
        return Jsons.toMap(StringUtil.toString(HttpUtil.get(url)));
    }
}