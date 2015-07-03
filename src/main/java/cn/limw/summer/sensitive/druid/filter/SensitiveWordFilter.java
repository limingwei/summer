package cn.limw.summer.sensitive.druid.filter;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import cn.limw.summer.sensitive.SensitiveWord;
import cn.limw.summer.sensitive.SensitiveWordHandler;
import cn.limw.summer.sensitive.handler.DefaultSensitiveWordHandler;

import com.alibaba.druid.filter.FilterAdapter;
import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;

/**
 * 用DruidFilter过滤敏感词
 * @author li
 * @version 1 (2014年10月17日 下午1:33:35)
 * @since Java7
 */
public class SensitiveWordFilter extends FilterAdapter {
    private Set<String> sensitiveWords = new HashSet<String>();

    private SensitiveWordHandler sensitiveWordHandler = DefaultSensitiveWordHandler.INSTANCE;

    public void preparedStatement_setString(FilterChain chain, PreparedStatementProxy statement, int parameterIndex, String x) throws SQLException {
        String value = handleSensitiveWord(getSensitiveWords(), x);
        super.preparedStatement_setString(chain, statement, parameterIndex, value);
    }

    private String handleSensitiveWord(Set<String> sensitiveWords, String word) throws SQLException {
        for (String sensitiveWord : sensitiveWords) {
            if (word.contains(sensitiveWord)) {
                return getSensitiveWordHandler().handle(new SensitiveWord().setWord(word).setRule(sensitiveWord));
            }
        }
        return word;
    }

    public Set<String> getSensitiveWords() {
        return sensitiveWords;
    }

    public void setSensitiveWords(Set<String> sensitiveWords) {
        this.sensitiveWords = sensitiveWords;
    }

    public SensitiveWordHandler getSensitiveWordHandler() {
        return sensitiveWordHandler;
    }

    public void setSensitiveWordHandler(SensitiveWordHandler sensitiveWordHandler) {
        this.sensitiveWordHandler = sensitiveWordHandler;
    }
}