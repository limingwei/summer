package cn.limw.summer.javax.websocket.server;

import java.io.IOException;
import java.net.URI;
import java.util.Set;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.Extension;
import javax.websocket.Session;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @author li
 * @version 1 (2015年2月11日 下午4:32:03)
 * @since Java7
 */
public class VoidServerContainer implements ServerContainer {
    public static final ServerContainer INSTANCE = new VoidServerContainer();

    public long getDefaultAsyncSendTimeout() {
        return 0;
    }

    public void setAsyncSendTimeout(long timeoutmillis) {}

    public Session connectToServer(Object annotatedEndpointInstance, URI path) throws DeploymentException, IOException {
        return null;
    }

    public Session connectToServer(Class<?> annotatedEndpointClass, URI path) throws DeploymentException, IOException {
        return null;
    }

    public Session connectToServer(Endpoint endpointInstance, ClientEndpointConfig cec, URI path) throws DeploymentException, IOException {
        return null;
    }

    public Session connectToServer(Class<? extends Endpoint> endpointClass, ClientEndpointConfig cec, URI path) throws DeploymentException, IOException {
        return null;
    }

    public long getDefaultMaxSessionIdleTimeout() {
        return 0;
    }

    public void setDefaultMaxSessionIdleTimeout(long timeout) {}

    public int getDefaultMaxBinaryMessageBufferSize() {
        return 0;
    }

    public void setDefaultMaxBinaryMessageBufferSize(int max) {}

    public int getDefaultMaxTextMessageBufferSize() {
        return 0;
    }

    public void setDefaultMaxTextMessageBufferSize(int max) {}

    public Set<Extension> getInstalledExtensions() {
        return null;
    }

    public void addEndpoint(Class<?> endpointClass) throws DeploymentException {}

    public void addEndpoint(ServerEndpointConfig serverConfig) throws DeploymentException {}
}