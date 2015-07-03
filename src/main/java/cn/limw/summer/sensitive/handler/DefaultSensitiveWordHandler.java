package cn.limw.summer.sensitive.handler;

import org.slf4j.Logger;

import cn.limw.summer.sensitive.SensitiveWord;
import cn.limw.summer.sensitive.SensitiveWordException;
import cn.limw.summer.sensitive.SensitiveWordHandler;
import cn.limw.summer.util.Logs;

/**
 * 默认抛异常
 * @author li
 * @version 1 (2014年10月23日 下午4:45:20)
 * @since Java7
 */
public class DefaultSensitiveWordHandler implements SensitiveWordHandler {
    private static final Logger log = Logs.slf4j();

    public static final SensitiveWordHandler INSTANCE = new DefaultSensitiveWordHandler();

    public String handle(SensitiveWord sensitiveWord) {
        log.error("sensitive word found, [" + sensitiveWord.getRule() + "] in [" + sensitiveWord.getWord() + "]");
        throw new SensitiveWordException(sensitiveWord);
    }
}