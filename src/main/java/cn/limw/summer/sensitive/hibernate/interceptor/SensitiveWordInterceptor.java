package cn.limw.summer.sensitive.hibernate.interceptor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import cn.limw.summer.sensitive.SensitiveWord;
import cn.limw.summer.sensitive.SensitiveWordHandler;
import cn.limw.summer.sensitive.handler.DefaultSensitiveWordHandler;

/**
 * 用HibernateInterceptor过滤敏感词
 * @author li
 * @version 1 (2014年10月17日 下午2:15:10)
 * @since Java7
 */
public class SensitiveWordInterceptor extends EmptyInterceptor {
    private static final long serialVersionUID = -5412075754501822209L;

    private SensitiveWordHandler sensitiveWordHandler = DefaultSensitiveWordHandler.INSTANCE;

    private Set<String> sensitiveWords = new HashSet<String>();

    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        for (int i = 0; i < state.length; i++) {
            if (state[i] instanceof String) {
                String word = (String) state[i];
                for (String sensitiveWord : sensitiveWords) {
                    if (word.contains(sensitiveWord)) {
                        String value = handleSensitiveWord(sensitiveWord, word);
                        state[i] = value;
                    }
                }
            }
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }

    private String handleSensitiveWord(String sensitiveWord, String word) {
        return getSensitiveWordHandler().handle(new SensitiveWord().setWord(word).setRule(sensitiveWord));
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