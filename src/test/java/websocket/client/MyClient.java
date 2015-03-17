package websocket.client;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class MyClient {

	@OnOpen
	public void onOpen(Session session) {
		try {
			String name = "Duke";
			System.out.println("Sending message to endpoint: " + name);
			session.getBasicRemote().sendText(name);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws DeploymentException,
			IOException {
		WebSocketContainer container = ContainerProvider
				.getWebSocketContainer();
		String uri = "ws://localhost:8080/webchat/websocket/chat";
		container.connectToServer(MyClient.class, URI.create(uri));
	}
}
