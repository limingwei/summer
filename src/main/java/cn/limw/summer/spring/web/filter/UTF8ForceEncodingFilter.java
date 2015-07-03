package cn.limw.summer.spring.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author li
 * @version 1 (2014年12月29日 下午1:07:11)
 * @since Java7
 * @see org.springframework.web.filter.CharacterEncodingFilter
 */
public class UTF8ForceEncodingFilter extends OncePerRequestFilter {
    private String encoding = "UTF8";

    private Boolean forceEncoding = true;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (getEncoding() != null && (getForceEncoding() || request.getCharacterEncoding() == null)) {
            request.setCharacterEncoding(getEncoding());
            if (getForceEncoding()) {
                response.setCharacterEncoding(getEncoding());
            }
        }
        filterChain.doFilter(request, response);
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Boolean getForceEncoding() {
        return forceEncoding;
    }

    public void setForceEncoding(Boolean forceEncoding) {
        this.forceEncoding = forceEncoding;
    }
}