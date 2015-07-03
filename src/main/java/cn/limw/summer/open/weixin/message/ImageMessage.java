package cn.limw.summer.open.weixin.message;

/**
 * ImageMessage
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月17日 下午8:00:47)
 */
public class ImageMessage extends MediaMessage {
    private static final long serialVersionUID = -4121104210573203909L;

    private String picUrl;

    public String getType() {
        return MessageType.IMAGE;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) + "[id=" + getId() + ", type=" + getType() + ", from=" + getFrom() + ", to=" + getTo() // 
                + ", createTime=" + getCreateTime() + ", picUrl=" + getPicUrl() + "]";

        //        return "<xml>"//
        //                + "<ToUserName><![CDATA[" + this.getTo() + "]]></ToUserName>"//
        //                + "<FromUserName><![CDATA[" + this.getFrom() + "]]></FromUserName>"//
        //                + "<CreateTime>" + this.getCreateTime() + "</CreateTime>"//
        //                + "<MsgType><![CDATA[" + this.getType() + "]]></MsgType>"//
        //                + "<PicUrl><![CDATA[" + this.getPicUrl() + "]]></PicUrl>"//
        //                + "<MsgId><![CDATA[" + this.getId() + "]]></MsgId>"//
        //                + "</xml>";
    }
}