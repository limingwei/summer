package cn.limw.summer.open.weixin.message;

/**
 * @author li
 * @version 1 (2015年5月29日 下午5:35:30)
 * @since Java7
 */
public class LocationMessage extends Message {
    private static final long serialVersionUID = -3956031663770324272L;

    private String label;

    private String scale;

    private String locationX;

    private String locationY;

    public String getType() {
        return MessageType.LOCATION;
    }

    public String getLabel() {
        return label;
    }

    public String getLocationX() {
        return locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public String getScale() {
        return scale;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) + "[id=" + getId() + ", type=" + getType() + ", from=" + getFrom() + ", to=" + getTo() // 
                + ", createTime=" + getCreateTime() + ", locationX" + getLocationX() + ", locationY" + getLocationY() + ", label" + getLabel() + ", scale" + getScale() + "]";
    }
}