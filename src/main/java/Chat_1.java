import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * 
 * 注解
 * 
 * @author zengxm 2015年3月13日
 * 
 */
@ServerEndpoint(value = "/chat01")
public class Chat_1 {
	private static final AtomicInteger connectionIds = new AtomicInteger(0);
	private static final Set<Chat_1> connections = new CopyOnWriteArraySet<Chat_1>();

	private final String nickname;
	private Session session;

	public Chat_1() {
		nickname = "游客ID：" + connectionIds.getAndIncrement();
	}

	@OnOpen
	public void start(Session session) {
		this.session = session;
		connections.add(this);
		String message = String.format("嗨嗨，姑娘们，来接客了： %s %s", nickname,
				"has joined.");
		broadcast(message);
	}

	@OnClose
	public void end() {
		connections.remove(this);
		String message = String.format("客官慢走，嘿嘿，还没付钱呢： %s %s", nickname,
				"has disconnected.");
		broadcast(message);
	}

	@OnMessage
	public void receive(String message) {
		// Never trust the client
		String filteredMessage = String.format("您有新消息：%s: %s", nickname,
				message.toString());
		broadcast(filteredMessage);
	}

	private static void broadcast(String msg) {
		for (Chat_1 client : connections) {
			try {
				client.session.getBasicRemote().sendText(msg);
			} catch (IOException e) {
				connections.remove(client);
				try {
					client.session.close();
				} catch (IOException e1) {
					// Ignore
				}
				String message = String.format("* %s %s", client.nickname,
						"has been disconnected.");
				broadcast(message);
			}// try
		}// for
	}// void broadcast(String msg)
}
