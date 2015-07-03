package cn.limw.summer.open.push.baidu;

import java.util.Map;

import cn.limw.summer.java.net.util.NetUrlUtil;
import cn.limw.summer.util.MD5Util;
import cn.limw.summer.util.Maps;

/**
 * @author li
 * @version 1 (2014年11月27日 上午11:50:39)
 * @since Java7
 */
public class BaiduPushUtil {
    public static String buildParameters(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = false;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (isFirst == false) {
                isFirst = true;
            } else {
                sb.append('&');
            }
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(NetUrlUtil.encode(entry.getValue()));
        }
        return sb.toString();
    }

    public static String digest(String method, String url, String clientSecret, Map<String, String> params) {
        params = Maps.sortMap(params);// 先将参数排序

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(method).append(url);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            if (!"url".equals(key) && !"http_method".equals(key)) {
                stringBuilder.append(entry.getKey()).append('=').append(entry.getValue());
            }
        }
        stringBuilder.append(clientSecret);
        String encodeString = NetUrlUtil.encode(stringBuilder.toString());
        if (encodeString != null) {
            encodeString = encodeString.replaceAll("\\*", "%2A");
        }
        return MD5Util.toMD5Hex(encodeString);
    }

    /**
     * notification_basic_style:
     * 静音：----------------------------1: 可清除
     * 振动：----------2: 不可清除,-----3: 可清除
     * 响铃：----------4: 不可清除,-----5: 可清除
     * 震动和响铃：---6: 不可清除,-----7: 可清除
     */
    public static Integer notificationBasicStyle(Boolean soundOn, Boolean vibrateOn) {
        if (null == soundOn || null == vibrateOn) {
            return 7;
        }

        if (!soundOn && !vibrateOn) {
            return 1;
        } else if (!soundOn && vibrateOn) {
            return 3;
        } else if (soundOn && !vibrateOn) {
            return 5;
        }
        return 7;
    }
}