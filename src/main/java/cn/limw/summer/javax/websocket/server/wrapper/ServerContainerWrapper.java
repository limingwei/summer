package cn.limw.summer.javax.websocket.server.wrapper;

import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

import cn.limw.summer.javax.websocket.wrapper.WebSocketContainerWrapper;
import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2015年2月11日 下午1:26:04)
 * @since Java7
 */
public class ServerContainerWrapper extends WebSocketContainerWrapper implements ServerContainer {
    private ServerContainer serverContainer;

    public ServerContainer getServerContainer() {
        return Asserts.noNull(serverContainer);
    }

    public void setServerContainer(ServerContainer serverContainer) {
        super.setWebSocketContainer(serverContainer);
        this.serverContainer = serverContainer;
    }

    public ServerContainerWrapper() {}

    public ServerContainerWrapper(ServerContainer serverContainer) {
        setServerContainer(serverContainer);
    }

    public void addEndpoint(Class<?> endpointClass) throws DeploymentException {
        getServerContainer().addEndpoint(endpointClass);
    }

    public void addEndpoint(ServerEndpointConfig serverConfig) throws DeploymentException {
        getServerContainer().addEndpoint(serverConfig);
    }
}