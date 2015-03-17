package websocket;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import websocket.chat.ChatServer;

/**
 * JSR-356
 * 
 * 实现websocket的配置定义, 据说tomcat启动, 会自动扫描
 * 
 * @author zengxm 2015年3月17日
 * 
 */
public class WebSocketConfig implements ServerApplicationConfig {

	private Logger log = LoggerFactory.getLogger(WebSocketConfig.class);

	public WebSocketConfig() {
		log.debug("Web Socket Config init");
	}

	@Override
	public Set<ServerEndpointConfig> getEndpointConfigs(
			Set<Class<? extends Endpoint>> endpointClasses) {
		Set<ServerEndpointConfig> result = new HashSet<ServerEndpointConfig>();
		if (endpointClasses.contains(ChatServer.class)) {
			result.add(ServerEndpointConfig.Builder.create(ChatServer.class,
					"/websocket/chat").build());
		}
		log.debug("End Point implement " + result); 
		return result;
	}

	@Override
	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
		Set<Class<?>> result = new HashSet<Class<?>>();
		for (Class<?> clazz : scanned) {
			if (clazz.getPackage().getName().startsWith("websocket.")) {
				result.add(clazz);
			}
		}
		log.debug("End Point annotated " + result);
		return result;
	}

}
