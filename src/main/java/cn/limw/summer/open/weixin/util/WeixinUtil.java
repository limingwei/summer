package cn.limw.summer.open.weixin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;

import cn.limw.summer.java.collection.NiceToStringList;
import cn.limw.summer.open.weixin.event.ClickEvent;
import cn.limw.summer.open.weixin.event.EventType;
import cn.limw.summer.open.weixin.event.LocationEvent;
import cn.limw.summer.open.weixin.event.ScanEvent;
import cn.limw.summer.open.weixin.event.SubscribeEvent;
import cn.limw.summer.open.weixin.event.UnSubscribeEvent;
import cn.limw.summer.open.weixin.message.EventMessage;
import cn.limw.summer.open.weixin.message.ImageMessage;
import cn.limw.summer.open.weixin.message.LinkMessage;
import cn.limw.summer.open.weixin.message.LocationMessage;
import cn.limw.summer.open.weixin.message.Message;
import cn.limw.summer.open.weixin.message.MessageType;
import cn.limw.summer.open.weixin.message.ShortVideoMessage;
import cn.limw.summer.open.weixin.message.TextMessage;
import cn.limw.summer.open.weixin.message.VideoMessage;
import cn.limw.summer.open.weixin.message.VoiceMessage;
import cn.limw.summer.util.ArrayUtil;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年5月27日 下午4:14:06)
 * @since Java7
 */
public class WeixinUtil {
    private static final Logger log = Logs.slf4j();

    /**
     * http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html#.E5.AE.A2.E6.9C.8D.E6.8E.A5.E5.8F.A3-.E5.8F.91.E6.B6.88.E6.81.AF
     */
    public static String messageToJson(Message message) {
        Asserts.noNull(message);

        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            return "{ \"touser\":\"" + textMessage.getTo() + "\", \"msgtype\":\"text\", \"text\": {\"content\":\"" + textMessage.getContent() + "\" }}";
        } else if (message instanceof ImageMessage) {
            ImageMessage imageMessage = (ImageMessage) message;
            return "{ \"touser\":\"" + imageMessage.getTo() + "\", \"msgtype\":\"image\", \"image\": { \"media_id\":\"" + imageMessage.getMediaId() + "\" }}";
        } else if (message instanceof VoiceMessage) {
            VoiceMessage voiceMessage = (VoiceMessage) message;
            return "{ \"touser\":\"" + voiceMessage.getTo() + "\", \"msgtype\":\"voice\", \"voice\": { \"media_id\":\"" + voiceMessage.getMediaId() + "\" }}";
        } else if (message instanceof VideoMessage) {
            VideoMessage videoMessage = (VideoMessage) message;
            return "{ \"touser\":\"" + videoMessage.getTo() + "\", \"msgtype\":\"video\", \"video\": { \"media_id\":\"" + videoMessage.getMediaId() + "\", \"thumb_media_id\":\"" + videoMessage.getThumbMediaId() //
                    + "\", \"title\":\"" + videoMessage.getTitle() + "\", \"description\":\"" + videoMessage.getDescription() + "\" }}";
        } else {
            throw new RuntimeException("未知的消息类型 " + message + ", " + message.getClass().getName());
        }
    }

    public static Message stringToMessage(String requestBody) {
        try {
            Message message = null;
            Document document = Jsoup.parse(requestBody);
            Elements xml = document.select("xml");
            String type = xml.select("MsgType").text();
            if (MessageType.TEXT.equalsIgnoreCase(type)) {
                message = new TextMessage();
                ((TextMessage) message).setContent(xml.select("Content").text());
            } else if (MessageType.IMAGE.equalsIgnoreCase(type)) {
                message = new ImageMessage();
                ((ImageMessage) message).setMediaId(xml.select("MediaId").text());
                ((ImageMessage) message).setPicUrl(xml.select("PicUrl").text());
            } else if (MessageType.VOICE.equalsIgnoreCase(type)) {
                message = new VoiceMessage();
                ((VoiceMessage) message).setMediaId(xml.select("MediaId").text());
                ((VoiceMessage) message).setFormat(xml.select("Format").text());
                ((VoiceMessage) message).setRecognition(xml.select("Recognition").text());
            } else if (MessageType.VIDEO.equalsIgnoreCase(type)) {
                message = new VideoMessage();
                ((VideoMessage) message).setMediaId(xml.select("MediaId").text());
                ((VideoMessage) message).setThumbMediaId(xml.select("ThumbMediaId").text());
            } else if (MessageType.SHORT_VIDEO.equalsIgnoreCase(type)) {
                message = new ShortVideoMessage();
                ((VideoMessage) message).setMediaId(xml.select("MediaId").text());
                ((VideoMessage) message).setThumbMediaId(xml.select("ThumbMediaId").text());
            } else if (MessageType.LOCATION.equalsIgnoreCase(type)) {
                message = new LocationMessage();
                ((LocationMessage) message).setLocationX(xml.select("Location_X").text());
                ((LocationMessage) message).setLocationY(xml.select("Location_Y").text());
                ((LocationMessage) message).setLabel(xml.select("Label").text());
                ((LocationMessage) message).setScale(xml.select("Scale").text());
            } else if (MessageType.LINK.equalsIgnoreCase(type)) {
                message = new LinkMessage();
                ((LinkMessage) message).setTitle(xml.select("Title").text());
                ((LinkMessage) message).setDescription(xml.select("Description").text());
                ((LinkMessage) message).setUrl(xml.select("Url").text());
            } else if (MessageType.EVENT.equalsIgnoreCase(type)) {
                message = stringToEvent(requestBody, message, xml);
            } else {
                log.error("未处理的消息类型 " + requestBody);
            }

            if (null != message) {
                message.setId(xml.select("MsgId").text());
                message.setFrom(xml.select("FromUserName").text());
                message.setTo(xml.select("ToUserName").text());
                message.setCreateTime(Long.valueOf(xml.select("CreateTime").text()));
            }
            return message;
        } catch (Exception e) {
            log.error("解析消息出错 \n" + requestBody + "\n", e);
            return null;
        }
    }

    public static Message stringToEvent(String requestBody, Message message, Elements xml) {
        String event = xml.select("Event").text();
        if (EventType.SUBSCRIBE.equalsIgnoreCase(event)) {
            message = new SubscribeEvent();
            Elements ticket = xml.select("Ticket");
            if (null != ticket) {
                ((SubscribeEvent) message).setTicket(ticket.text());
            }
        } else if (EventType.SCAN.equalsIgnoreCase(event)) {
            message = new ScanEvent();
            Elements ticket = xml.select("Ticket");
            if (null != ticket) {
                ((ScanEvent) message).setTicket(ticket.text());
            }
        } else if (EventType.UNSUBSCRIBE.equalsIgnoreCase(event)) {
            message = new UnSubscribeEvent();
        } else if (EventType.CLICK.equalsIgnoreCase(event)) {
            message = new ClickEvent();
        } else if (EventType.LOCATION.equalsIgnoreCase(event)) {
            message = new LocationEvent();
            ((LocationEvent) message).setLatitude(xml.select("Latitude").text());
            ((LocationEvent) message).setLongitude(xml.select("Longitude").text());
            ((LocationEvent) message).setPrecision(xml.select("Precision").text());
        } else {
            log.error("未处理的事件类型 " + requestBody);
        }
        if (null != message) {
            ((EventMessage) message).setEvent(event);
            Elements eventKey = xml.select("EventKey");
            if (null != eventKey) {
                ((EventMessage) message).setEventKey(xml.select("EventKey").text());
            }
        }
        return message;
    }

    /**
     * checkAuthentication
     */
    public static Boolean checkAuthentication(String token, String signature, String timestamp, String nonce) {
        Asserts.noAnyEmpty(new String[] { token, timestamp, nonce }, "token, timestamp, nonce 是必须的");
        String[] array = ArrayUtil.sort(token, timestamp, nonce); // 将获取到的参数放入数组

        String pwd = encrypt(StringUtil.concat(array)); // 对排序后的字符串进行SHA-1加密
        if (pwd.equalsIgnoreCase(signature)) {
            return true;
        } else {
            log.info("array={}, pwd={}, signature={}", new NiceToStringList(array), pwd, signature);
            return false;
        }
    }

    public static String encrypt(String content) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] digest = messageDigest.digest(content.toString().getBytes());
            return byteToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String byteToString(byte[] byteArray) {
        String stringDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            stringDigest += byteToHexString(byteArray[i]);
        }
        return stringDigest;
    }

    private static String byteToHexString(byte _byte) {
        char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] chars = new char[2];
        chars[0] = digit[(_byte >>> 4) & 0X0F];
        chars[1] = digit[_byte & 0X0F];

        return new String(chars);
    }
}