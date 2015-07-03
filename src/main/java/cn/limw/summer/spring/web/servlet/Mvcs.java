package cn.limw.summer.spring.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.limw.summer.java.collection.NiceToStringMap;
import cn.limw.summer.javax.servlet.http.wrapper.HttpSessionWrapper;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Files;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Maps;
import cn.limw.summer.util.Nums;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年6月26日 下午1:49:01)
 * @since Java7
 */
public class Mvcs {
    private static final Logger log = Logs.slf4j();

    private static final ThreadLocal<HttpServletRequest> REQUEST_THREAD_LOCAL = new ThreadLocal<HttpServletRequest>();

    private static final ThreadLocal<HttpServletResponse> RESPONSE_THREAD_LOCAL = new ThreadLocal<HttpServletResponse>();

    public static void bind(ServletRequest request, ServletResponse response) {
        setRequest(request);
        setResponse(response);
    }

    public static void setRequest(ServletRequest request) {
        REQUEST_THREAD_LOCAL.set((HttpServletRequest) request);
    }

    public static void setResponse(ServletResponse response) {
        RESPONSE_THREAD_LOCAL.set((HttpServletResponse) response);
    }

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = REQUEST_THREAD_LOCAL.get();
        if (null == request) {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (null != requestAttributes && requestAttributes instanceof HttpServletRequest) {
                request = (HttpServletRequest) requestAttributes;
            } else if (null != requestAttributes && requestAttributes instanceof ServletRequestAttributes) {
                request = ((ServletRequestAttributes) requestAttributes).getRequest();
            }
        }
        return request;
    }

    public static HttpServletResponse getResponse() {
        return RESPONSE_THREAD_LOCAL.get();
    }

    public static HttpSession getSession() {
        return new HttpSessionWrapper(getRequest().getSession());
    }

    public static void setRequest(String name, Object value) {
        getRequest().setAttribute(name, value);
    }

    public static void setSession(String name, Object value) {
        getSession().setAttribute(name, value);
    }

    public static Object getSession(String name) {
        return getSession().getAttribute(name);
    }

    public static Map<String, String[]> getParameterMap() {
        return (Map<String, String[]>) new NiceToStringMap(new HashMap<String, String[]>(getRequest().getParameterMap()));
    }

    public static Integer getServerPort() {
        return getRequest().getServerPort();
    }

    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    public static void removeSession(String name) {
        getSession().removeAttribute(name);
    }

    /**
     * 在本机运行:开发测试环境
     */
    public static Boolean isLocalhost() {
        String serverName = getServerName();
        return serverName.equalsIgnoreCase("localhost") || serverName.equalsIgnoreCase("127.0.0.1");
    }

    public static void invalidateSession() {
        getSession().invalidate();
    }

    public static void write(String text) {
        Files.write(text.getBytes(), getOutputStream());
    }

    public static void write(Object object) {
        write(object + "");
    }

    public static void write(byte[] data) {
        Files.write(data, getOutputStream());
    }

    public static String getRequestedSessionId() {
        return getRequest().getRequestedSessionId();
    }

    public static String getRemoteAddr() {
        return getRequest().getRemoteAddr();
    }

    public static String getRequestURI() {
        return getRequest().getRequestURI();
    }

    public static void setContentType(String type) {
        getResponse().setContentType(type);
    }

    public static void forward(String path) {
        try {
            getRequest().getRequestDispatcher(path).forward(getRequest(), getResponse());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void redirect(String redirect) {
        try {
            getResponse().sendRedirect(redirect);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean isPost() {
        return getRequest().getMethod().equalsIgnoreCase("POST");
    }

    public static Integer getInt(String name) {
        return new Integer(getParameter(name));
    }

    public static Long getLong(String key) {
        return Nums.toLong(getParameter(key));
    }

    public static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }

    public static InputStream getResourceAsStream(String path) {
        return getServletContext().getResourceAsStream(path);
    }

    public static String getMethod() {
        return getRequest().getMethod();
    }

    public static Integer getIntOrNull(String name) {
        try {
            return getInt(name);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getRealPath(String path) {
        return getServletContext().getRealPath(path);
    }

    public static ServletOutputStream getOutputStream() {
        return getOutputStream(getResponse());
    }

    public static ServletOutputStream getOutputStream(HttpServletResponse response) {
        try {
            return response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> getResponseHeaders(HttpServletResponse response) {
        Map<String, Object> headers = new HashMap<String, Object>();
        Collection<String> headerNames = response.getHeaderNames();
        for (String headerName : headerNames) {
            headers.put(headerName, response.getHeaders(headerName));
        }
        return headers;
    }

    public static String[] getRequestHeaders(HttpServletRequest request, String name) {
        List<String> list = new ArrayList<String>();
        Enumeration<String> headers = request.getHeaders(name);
        while (headers.hasMoreElements()) {
            String value = (String) headers.nextElement();
            list.add(value);
        }
        return list.toArray(new String[0]);
    }

    public static Map<String, String[]> getRequestHeaders() {
        return getRequestHeaders(getRequest());
    }

    public static Map<String, Object> getSessionMap() {
        return getSessionMap(getSession());
    }

    public static Map<String, Object> getSessionMap(HttpServletRequest request) {
        return getSessionMap(request.getSession());
    }

    public static Map<String, Object> getSessionMap(HttpSession session) {
        Map<String, Object> sessionMap = new HashMap<String, Object>();
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = (String) attributeNames.nextElement();
            sessionMap.put(attributeName, getSession(attributeName));
        }
        return new NiceToStringMap(sessionMap);
    }

    public static String getParameter(String key, String defaultValue) {
        String value = getParameter(key);
        return StringUtil.isEmpty(value) ? defaultValue : value;
    }

    public static String getRequestURI(ServletRequest request) {
        return ((HttpServletRequest) request).getRequestURI();
    }

    public static Cookie[] getCookies() {
        return getRequest().getCookies();
    }

    public static Cookie getCookie(String name) {
        Cookie[] cookies = getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static String getCookieValue(String name) {
        Cookie cookie = getCookie(name);
        return null == cookie ? null : cookie.getValue();
    }

    public static String getScheme() {
        return getRequest().getScheme();
    }

    public static String getRequestHeader(String name) {
        return getRequest().getHeader(name);
    }

    public static Map<String, Object> getParameterMap(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Set<Entry<String, String[]>> parameters = request.getParameterMap().entrySet();
        Map<String, Object> map = new HashMap<String, Object>();
        for (Entry<String, String[]> entry : parameters) {
            String[] value = entry.getValue();
            if (value.length > 1) {
                map.put(entry.getKey(), value);
            } else {
                map.put(entry.getKey(), value[0]);
            }
        }
        return new NiceToStringMap(map);
    }

    public static void setResponseHeader(String name, String value) {
        try {
            value = new String(value.getBytes("ISO-8859-1"), "utf8");
            getResponse().setHeader(name, value);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getRequestAttribute(String name) {
        return getRequest().getAttribute(name);
    }

    public static Map<String, Object> getServletContextAttributes(ServletContext servletContext) {
        Map<String, Object> attributes = new HashMap<String, Object>();
        Enumeration<String> attributeNames = servletContext.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = (String) attributeNames.nextElement();
            attributes.put(attributeName, servletContext.getAttribute(attributeName));
        }
        return attributes;
    }

    public static Integer getIntOrDefault(String key, Integer defaultValue) {
        String value = getParameter(key);
        if (StringUtil.isEmpty(value)) {
            return defaultValue;
        }
        return Nums.toInt(value);
    }

    public static void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        getResponse().addCookie(cookie);
    }

    /**
     * @param name
     * @param value
     * @param expiry 秒
     */
    public static void addCookie(String name, String value, Integer expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiry);
        getResponse().addCookie(cookie);
    }

    public static void removeCookie(String name) {
        Cookie cookie = new Cookie(name, "remove-cookie-" + name);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(false);
        getResponse().addCookie(cookie);
    }

    public static Object getAndRemoveSession(String key) {
        Object value = getSession(key);
        removeSession(key);
        return value;
    }

    public static Object getCookieValueAndRemove(String name) {
        String value = getCookieValue(name);
        removeCookie(name);
        return value;
    }

    public static Map<String, Object> getRequestAttributes(HttpServletRequest request) {
        Map<String, Object> attributes = new HashMap<String, Object>();
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = (String) attributeNames.nextElement();
            attributes.put(attributeName, request.getAttribute(attributeName));
        }
        return attributes;
    }

    public static Object getServletContextAttribute(String name) {
        return getServletContext().getAttribute(name);
    }

    public static String getReferer() {
        HttpServletRequest request = getRequest();
        if (null == request) {
            return null;
        }
        String header = request.getHeader("referer");
        return StringUtil.isEmpty(header) ? request.getHeader("Referer") : header;
    }

    /**
     * Maps.remove()
     */
    public static Map<String, String[]> getRequestHeadersWithoutCookie() {
        return Maps.remove(getRequestHeaders(getRequest()), "cookie");
    }

    public static Map<String, String[]> getRequestHeaders(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Map<String, String[]> headers = new HashMap<String, String[]>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            headers.put(name, getRequestHeaders(request, name));
        }
        return new NiceToStringMap(headers);
    }

    public static void setServletContextAttribute(String key, Object value) {
        Asserts.noNull(key, "key 不可以为空");
        getServletContext().setAttribute(key, value);
    }

    public static void removeServletContextAttribute(String key) {
        Asserts.noNull(key, "key 不可以为空");
        getServletContext().removeAttribute(key);
    }

    public static String getRequestBody() {
        return Files.toString(getInputStream(), "UTF-8");
    }

    public static String getRequestBody(HttpServletRequest request) {
        return Files.toString(getInputStream(request), "UTF-8");
    }

    public static ServletInputStream getInputStream(HttpServletRequest request) {
        try {
            return request.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ServletInputStream getInputStream() {
        return getInputStream(getRequest());
    }

    public static String getServerName() {
        try {
            HttpServletRequest request = getRequest();
            if (null == request) {
                log.error("request 为空, 返回localhost, 应该只在测试时出现");
                return "localhost";
            }
            return request.getServerName();
        } catch (Exception e) {
            if ("No uri".equalsIgnoreCase(e.getMessage())) { // java.lang.IllegalStateException: No uri at org.eclipse.jetty.server.Request.getServerName(Request.java:1200) 不知道为啥
                return "localhost";
            } else {
                throw e;
            }
        }
    }

    public static String getServerName(HttpServletRequest request) {
        return request.getServerName();
    }

    public static void setRequestAttribute(String key, Object value) {
        HttpServletRequest request = getRequest();
        request.setAttribute(key, value);
    }
}