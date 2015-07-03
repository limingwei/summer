package cn.limw.summer.sensitive.spring.mvc.interceptor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.sensitive.SensitiveWord;
import cn.limw.summer.sensitive.SensitiveWordHandler;
import cn.limw.summer.sensitive.handler.DefaultSensitiveWordHandler;
import cn.limw.summer.spring.web.servlet.AbstractHandlerInterceptor;

/**
 * @author li
 * @version 1 (2014年10月29日 上午10:51:33)
 * @since Java7
 */
public class SensitiveWordInterceptor extends AbstractHandlerInterceptor {
    private Set<String> sensitiveWords = new HashSet<String>();

    private SensitiveWordHandler sensitiveWordHandler = DefaultSensitiveWordHandler.INSTANCE;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(wrapRequest(request), response, handler);
    }

    private HttpServletRequestWrapper wrapRequest(HttpServletRequest request) {
        return new HttpServletRequestWrapper(request) {
            public String getParameter(String name) {
                return handleSensitiveWord(sensitiveWords, super.getParameter(name));
            }

            public String[] getParameterValues(String name) {
                return handleSensitiveWords(sensitiveWords, super.getParameterValues(name));
            };

            public Map<String, String[]> getParameterMap() {
                return new HashMap<String, String[]>(super.getParameterMap()) {
                    public String[] get(Object key) {
                        return handleSensitiveWords(sensitiveWords, super.get(key));
                    }
                };
            }
        };
    }

    protected String[] handleSensitiveWords(Set<String> sensitiveWords, String[] values) {
        for (int i = 0; i < values.length; i++) {
            values[i] = handleSensitiveWord(sensitiveWords, values[i]);
        }
        return values;
    }

    private String handleSensitiveWord(Set<String> sensitiveWords, String word) {
        for (String sensitiveWord : sensitiveWords) {
            if (word.contains(sensitiveWord)) {
                return getSensitiveWordHandler().handle(new SensitiveWord().setWord(word).setRule(sensitiveWord));
            }
        }
        return word;
    }

    public SensitiveWordHandler getSensitiveWordHandler() {
        return sensitiveWordHandler;
    }

    public void setSensitiveWordHandler(SensitiveWordHandler sensitiveWordHandler) {
        this.sensitiveWordHandler = sensitiveWordHandler;
    }

    public Set<String> getSensitiveWords() {
        return sensitiveWords;
    }

    public void setSensitiveWords(Set<String> sensitiveWords) {
        this.sensitiveWords = sensitiveWords;
    }
}