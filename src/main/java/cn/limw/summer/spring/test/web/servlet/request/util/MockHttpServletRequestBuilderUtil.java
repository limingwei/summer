package cn.limw.summer.spring.test.web.servlet.request.util;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriUtils;

import cn.limw.summer.spring.mock.web.MockRequest;
import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2015年2月26日 上午9:55:36)
 * @since Java7
 */
public class MockHttpServletRequestBuilderUtil {
    public static MockHttpServletRequest buildRequest(MockHttpServletRequestBuilder mockHttpServletRequestBuilder, ServletContext servletContext) {
        MockHttpServletRequest request = createServletRequest(servletContext);

        String requestUri = uriComponents(mockHttpServletRequestBuilder).getPath();
        request.setRequestURI(requestUri);
        updatePathRequestProperties(mockHttpServletRequestBuilder, request, requestUri);

        if (uriComponents(mockHttpServletRequestBuilder).getScheme() != null) {
            request.setScheme(uriComponents(mockHttpServletRequestBuilder).getScheme());
        }
        if (uriComponents(mockHttpServletRequestBuilder).getHost() != null) {
            request.setServerName(uriComponents(mockHttpServletRequestBuilder).getHost());
        }
        if (uriComponents(mockHttpServletRequestBuilder).getPort() != -1) {
            request.setServerPort(uriComponents(mockHttpServletRequestBuilder).getPort());
        }

        request.setMethod(method(mockHttpServletRequestBuilder).name());
        for (String name : headers(mockHttpServletRequestBuilder).keySet()) {
            for (Object value : headers(mockHttpServletRequestBuilder).get(name)) {
                request.addHeader(name, value);
            }
        }

        try {
            if (uriComponents(mockHttpServletRequestBuilder).getQuery() != null) {
                String query = UriUtils.decode(uriComponents(mockHttpServletRequestBuilder).getQuery(), "UTF-8");
                request.setQueryString(query);
            }

            for (Entry<String, List<String>> entry : uriComponents(mockHttpServletRequestBuilder).getQueryParams().entrySet()) {
                for (String value : entry.getValue()) {
                    value = (value != null) ? UriUtils.decode(value, "UTF-8") : null;
                    request.addParameter(UriUtils.decode(entry.getKey(), "UTF-8"), value);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            // shouldn't happen
        }

        for (String name : parameters(mockHttpServletRequestBuilder).keySet()) {
            for (String value : parameters(mockHttpServletRequestBuilder).get(name)) {
                request.addParameter(name, value);
            }
        }

        request.setContentType(contentType(mockHttpServletRequestBuilder));
        request.setContent(content(mockHttpServletRequestBuilder));
        request.setCookies(cookies(mockHttpServletRequestBuilder).toArray(new Cookie[cookies(mockHttpServletRequestBuilder).size()]));

        if (locale(mockHttpServletRequestBuilder) != null) {
            request.addPreferredLocale(locale(mockHttpServletRequestBuilder));
        }
        request.setCharacterEncoding(characterEncoding(mockHttpServletRequestBuilder));

        if (secure(mockHttpServletRequestBuilder) != null) {
            request.setSecure(secure(mockHttpServletRequestBuilder));
        }
        request.setUserPrincipal(principal(mockHttpServletRequestBuilder));

        for (String name : attributes(mockHttpServletRequestBuilder).keySet()) {
            request.setAttribute(name, attributes(mockHttpServletRequestBuilder).get(name));
        }

        // Set session before session and flash attributes
        if (session(mockHttpServletRequestBuilder) != null) {
            request.setSession(session(mockHttpServletRequestBuilder));
        }
        for (String name : sessionAttributes(mockHttpServletRequestBuilder).keySet()) {
            request.getSession().setAttribute(name, sessionAttributes(mockHttpServletRequestBuilder).get(name));
        }

        FlashMap flashMap = new FlashMap();
        flashMap.putAll(flashAttributes(mockHttpServletRequestBuilder));

        FlashMapManager flashMapManager = getFlashMapManager(mockHttpServletRequestBuilder, request);
        flashMapManager.saveOutputFlashMap(flashMap, request, new MockHttpServletResponse());

        // Apply post-processors at the very end
        for (RequestPostProcessor postProcessor : postProcessors(mockHttpServletRequestBuilder)) {
            request = postProcessor.postProcessRequest(request);
            if (request == null) {
                throw new IllegalStateException("Post-processor [" + postProcessor.getClass().getName() + "] returned null");
            }
        }

        request.setAsyncSupported(true);

        return request;
    }

    private static List<String> cookies(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (List<String>) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "cookies");
    }

    private static byte[] content(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (byte[]) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "content");
    }

    private static String contentType(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (String) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "contentType");
    }

    private static MultiValueMap<String, String> parameters(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (MultiValueMap<String, String>) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "parameters");
    }

    private static Locale locale(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (Locale) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "locale");
    }

    private static String characterEncoding(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (String) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "characterEncoding");
    }

    private static Boolean secure(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (Boolean) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "secure");
    }

    private static Principal principal(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (Principal) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "principal");
    }

    private static HashMap<String, Object> attributes(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (HashMap<String, Object>) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "attributes");
    }

    private static HttpSession session(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (HttpSession) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "session");
    }

    private static HashMap<String, Object> sessionAttributes(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (HashMap<String, Object>) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "sessionAttributes");
    }

    private static Map<? extends String, ? extends Object> flashAttributes(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (Map<? extends String, ? extends Object>) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "flashAttributes");
    }

    private static List<RequestPostProcessor> postProcessors(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (List<RequestPostProcessor>) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "postProcessors");
    }

    private static MultiValueMap<String, Object> headers(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (MultiValueMap<String, Object>) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "headers");
    }

    private static HttpMethod method(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (HttpMethod) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "method");
    }

    private static UriComponents uriComponents(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return (UriComponents) Mirrors.getFieldValue(mockHttpServletRequestBuilder, "uriComponents");
    }

    private static FlashMapManager getFlashMapManager(MockHttpServletRequestBuilder mockHttpServletRequestBuilder, MockHttpServletRequest mockHttpServletRequest) {
        Class<?>[] argTypes = { MockHttpServletRequest.class };
        Object[] args = { mockHttpServletRequest };
        return (FlashMapManager) Mirrors.invoke(MockHttpServletRequestBuilder.class, "getFlashMapManager", argTypes, mockHttpServletRequestBuilder, args);
    }

    /**
     * @see org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
     */
    private static void updatePathRequestProperties(MockHttpServletRequestBuilder mockHttpServletRequestBuilder, MockHttpServletRequest mockHttpServletRequest, String requestUri) {
        Class<?>[] argTypes = { MockHttpServletRequest.class, String.class };
        Object[] args = { mockHttpServletRequest, requestUri };
        Mirrors.invoke(MockHttpServletRequestBuilder.class, "updatePathRequestProperties", argTypes, mockHttpServletRequestBuilder, args);
    }

    private static MockHttpServletRequest createServletRequest(ServletContext servletContext) {
        return new MockRequest(servletContext);
    }
}