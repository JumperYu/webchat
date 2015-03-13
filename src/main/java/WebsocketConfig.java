import java.util.HashSet;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

public class WebsocketConfig implements ServerApplicationConfig {

	@Override
	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
		System.out.println("******getAnnotatedEndpointClasses******");
		// Deploy all WebSocket endpoints defined by annotations in the examples
		// web application. Filter out all others to avoid issues when running
		// tests on Gump
		// 这主要是扫描类的包，如果前缀为"com.websocket."就抓住她，然后做什么，你懂的
		Set<Class<?>> res = new HashSet<>();
		for (Class<?> cs : scanned) {
			//if (cs.getPackage().getName().startsWith("com.websocket.")) {
				res.add(cs);
		    //}
		}
		return res;
	}

	@Override
	public Set<ServerEndpointConfig> getEndpointConfigs(
			Set<Class<? extends Endpoint>> scanned) {
		System.out.println("******getEndpointConfigs******");
		Set<ServerEndpointConfig> res = new HashSet<>();
		/*
		 * //使用Programmatic api的服务器地址
		 * 
		 * if (scanned.contains(EchoEndpoint.class)) {
		 * res.add(ServerEndpointConfig.Builder.create( EchoEndpoint.class,
		 * "/websocket/echoProgrammatic").build()); }
		 */
		return res;
	}

}