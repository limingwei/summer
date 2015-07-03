package cn.limw.summer.zxing.util;

import java.util.HashMap;
import java.util.Map;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @author li
 * @version 1 (2015年5月20日 上午11:48:00)
 * @since Java7
 */
public class HintsBuilder {
    public static final Map<EncodeHintType, ?> UTF8_ERROR_CORRECTION_H_MARGIN_0 = new HintsBuilder().characterSet("utf-8").errorCorrectionLevel(ErrorCorrectionLevel.H).margin(0).toMap();

    private Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();

    public HintsBuilder characterSet(String charset) {
        hints.put(EncodeHintType.CHARACTER_SET, charset);
        return this;
    }

    public HintsBuilder errorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
        return this;
    }

    public HintsBuilder margin(Integer margin) {
        hints.put(EncodeHintType.MARGIN, margin);
        return this;
    }

    public Map<EncodeHintType, Object> toMap() {
        return new HashMap<EncodeHintType, Object>(hints);
    }
}