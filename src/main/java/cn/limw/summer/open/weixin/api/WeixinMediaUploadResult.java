package cn.limw.summer.open.weixin.api;

import java.io.Serializable;

import cn.limw.summer.json.JsonMap;
import cn.limw.summer.util.Jsons;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年6月26日 下午4:09:03)
 * @since Java7
 *        {"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789}
 *        {"errcode":40004,"errmsg":"invalid media type"}
 */
public class WeixinMediaUploadResult implements Serializable {
    private static final long serialVersionUID = -3932502926415928694L;

    private Boolean success;

    private String type;

    private String mediaId;

    private Long createdAt;

    private Integer errorCode;

    private String errorMessage;

    private String response;

    public WeixinMediaUploadResult(String response) {
        this.response = response;

        if (StringUtil.isEmpty(response)) {
            setSuccess(false);
            setErrorCode(-1);
            setErrorMessage("response is empty");
        } else if (!StringUtil.startWith(response, "{") || !StringUtil.endWith(response, "}")) {
            setSuccess(false);
            setErrorCode(-2);
            setErrorMessage("response is not json, " + response);
        } else {
            JsonMap jsonMap = Jsons.toJsonMap(response);
            Integer errcode = jsonMap.getInteger("errcode");
            if (null != errcode) {
                setSuccess(false);
                setErrorCode(errcode);
                setErrorMessage(jsonMap.getString("errmsg"));
            } else {
                setSuccess(true);
                setType(jsonMap.getString("type"));
                setMediaId(jsonMap.getString("media_id"));
                setCreatedAt(jsonMap.getLong("created_at"));
            }
        }
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getMediaId() {
        return mediaId;
    }

    public String getType() {
        return type;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return super.toString() + ", response=" + response;
    }
}