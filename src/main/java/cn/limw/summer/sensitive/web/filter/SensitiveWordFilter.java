package cn.limw.summer.sensitive.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import cn.limw.summer.sensitive.SensitiveWord;
import cn.limw.summer.sensitive.SensitiveWordHandler;
import cn.limw.summer.sensitive.handler.DefaultSensitiveWordHandler;
import cn.limw.summer.spring.util.ResourceUtil;
import cn.limw.summer.util.Files;
import cn.limw.summer.util.StringUtil;
import cn.limw.summer.web.filter.AbstractFilter;

/**
 * 用ServletFilter过滤敏感词
 * @author li
 * @version 1 (2014年10月17日 下午2:32:17)
 * @since Java7
 */
public class SensitiveWordFilter extends AbstractFilter {
    private String filePath = "";

    private Set<String> sensitiveWords = new HashSet<String>();

    private SensitiveWordHandler sensitiveWordHandler = DefaultSensitiveWordHandler.INSTANCE;

    public void init(FilterConfig filterConfig) throws ServletException {
        filePath = filterConfig.getInitParameter("filePath");
        if (StringUtil.isEmpty(filePath)) {
            filePath = "sensitive-words.txt";
        }
        sensitiveWords = Files.readFileToLines(ResourceUtil.classPathRead(filePath));
        super.init(filterConfig);
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(wrapRequest(request), response, chain);
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
}