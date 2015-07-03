package cn.limw.summer.sensitive.handler;

import cn.limw.summer.sensitive.SensitiveWord;
import cn.limw.summer.sensitive.SensitiveWordHandler;

/**
 * 内容替换关键词处理器
 * @author li
 * @version 1 (2014年10月23日 下午5:12:37)
 * @since Java7
 */
public class ReplaceSensitiveWordHandler implements SensitiveWordHandler {
    private String replacement = "{SensitiveWordReplacement}";

    public String handle(SensitiveWord sensitiveWord) {
        return getReplacement();
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }
}