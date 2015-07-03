package cn.limw.summer.open.weixin.message;

import java.io.Serializable;

/**
 * Article
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月18日 下午5:37:42)
 */
public class Article implements Serializable {
    private static final long serialVersionUID = 3256369802038266386L;

    private String title;

    private String description;

    private String picUrl;

    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) + "[title=" + getTitle() + ", description=" + getDescription() + ", picUrl=" + getPicUrl() + ", url=" + getUrl() + "]";
    }
}