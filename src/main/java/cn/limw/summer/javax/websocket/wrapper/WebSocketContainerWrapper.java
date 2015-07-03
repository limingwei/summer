package cn.limw.summer.javax.websocket.wrapper;

import java.io.IOException;
import java.net.URI;
import java.util.Set;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.Extension;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2015年2月11日 下午4:24:45)
 * @since Java7
 */
public class WebSocketContainerWrapper implements WebSocketContainer {
    private WebSocketContainer webSocketContainer;

    public WebSocketContainer getWebSocketContainer() {
        return Asserts.noNull(webSocketContainer);
    }

    public void setWebSocketContainer(WebSocketContainer webSocketContainer) {
        this.webSocketContainer = webSocketContainer;
    }

    public WebSocketContainerWrapper() {}

    public WebSocketContainerWrapper(WebSocketContainer webSocketContainer) {
        setWebSocketContainer(webSocketContainer);
    }

    public long getDefaultAsyncSendTimeout() {
        return getWebSocketContainer().getDefaultAsyncSendTimeout();
    }

    public void setAsyncSendTimeout(long timeoutmillis) {
        getWebSocketContainer().setAsyncSendTimeout(timeoutmillis);
    }

    public Session connectToServer(Object annotatedEndpointInstance, URI path) throws DeploymentException, IOException {
        return getWebSocketContainer().connectToServer(annotatedEndpointInstance, path);
    }

    public Session connectToServer(Class<?> annotatedEndpointClass, URI path) throws DeploymentException, IOException {
        return getWebSocketContainer().connectToServer(annotatedEndpointClass, path);
    }

    public Session connectToServer(Endpoint endpointInstance, ClientEndpointConfig cec, URI path) throws DeploymentException, IOException {
        return getWebSocketContainer().connectToServer(endpointInstance, cec, path);
    }

    public Session connectToServer(Class<? extends Endpoint> endpointClass, ClientEndpointConfig cec, URI path) throws DeploymentException, IOException {
        return getWebSocketContainer().connectToServer(endpointClass, cec, path);
    }

    public long getDefaultMaxSessionIdleTimeout() {
        return getWebSocketContainer().getDefaultMaxSessionIdleTimeout();
    }

    public void setDefaultMaxSessionIdleTimeout(long timeout) {
        getWebSocketContainer().setDefaultMaxSessionIdleTimeout(timeout);
    }

    public int getDefaultMaxBinaryMessageBufferSize() {
        return getWebSocketContainer().getDefaultMaxBinaryMessageBufferSize();
    }

    public void setDefaultMaxBinaryMessageBufferSize(int max) {
        getWebSocketContainer().setDefaultMaxBinaryMessageBufferSize(max);
    }

    public int getDefaultMaxTextMessageBufferSize() {
        return getWebSocketContainer().getDefaultMaxTextMessageBufferSize();
    }

    public void setDefaultMaxTextMessageBufferSize(int max) {
        getWebSocketContainer().setDefaultMaxTextMessageBufferSize(max);
    }

    public Set<Extension> getInstalledExtensions() {
        return getWebSocketContainer().getInstalledExtensions();
    }
}