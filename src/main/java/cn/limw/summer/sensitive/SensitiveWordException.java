package cn.limw.summer.sensitive;

/**
 * @author li
 * @version 1 (2014年10月23日 下午5:04:42)
 * @since Java7
 */
public class SensitiveWordException extends RuntimeException {
    private static final long serialVersionUID = -4950052664781532607L;

    public SensitiveWordException(SensitiveWord sensitiveWord) {
        super("checkSensitiveWord sensitive word found, [" + sensitiveWord.getRule() + "] in [" + sensitiveWord.getWord() + "]");
    }
}