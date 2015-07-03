package cn.limw.summer.sensitive;

/**
 * @author li
 * @version 1 (2014年10月23日 下午4:54:31)
 * @since Java7
 */
public class SensitiveWord {
    private String word;

    private String rule;

    public SensitiveWord setWord(String word) {
        this.word = word;
        return this;
    }

    public SensitiveWord setRule(String rule) {
        this.rule = rule;
        return this;
    }

    public String getWord() {
        return word;
    }

    public String getRule() {
        return rule;
    }
}