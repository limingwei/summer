package cn.limw.summer.open.weixin.event;

import cn.limw.summer.open.weixin.message.EventMessage;

/**
 * ImageMessage
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月17日 下午8:00:47)
 */
public class LocationEvent extends EventMessage {
    private static final long serialVersionUID = 9082963826570793715L;

    private String latitude;

    private String longitude;

    private String precision;

    public String getEvent() {
        return EventType.LOCATION;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }
}