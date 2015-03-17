package websocket.chat;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import websocket.WebSocketConfig;

/**
 * 采用实现ServerEndpoint
 * 
 * @author zengxm 2015年3月17日
 * 
 */
@ServerEndpoint(value = "/websocket/chat")
public class ChatServer {

	private static final Logger log = LoggerFactory
			.getLogger(WebSocketConfig.class);

	private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(1);
	private static final CopyOnWriteArraySet<Session> SESSIONS = new CopyOnWriteArraySet<Session>();
	private static final String PRE_FIX = "guest_";
	private String username;

	@OnOpen
	public void onOpen(Session session) {
		username = PRE_FIX + ATOMIC_INTEGER.getAndIncrement();
		log.debug("聊天服务器接到一个会话连接,访客:" + username);
		SESSIONS.add(session);
		broadcast("*" + username + "进入聊天室");
	}

	@OnMessage
	public void onMessage(String txt) {
		broadcast(username + ":" + txt);
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		log.debug("聊天服务器接到一个会话关闭,访客:" + username + ", 因为"
				+ closeReason.getReasonPhrase());
		SESSIONS.remove(session);
		broadcast("*" + username + "离开聊天室");
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		log.debug("聊天服务器接到一个会话异常,访客:" + username);
		SESSIONS.remove(session);
		// 不广播了
	}

	/**
	 * 根据所有session广播消息
	 * 
	 * @param msg
	 *            消息 - String
	 */
	public static void broadcast(String msg) {
		for (Session session : SESSIONS) {
			try {
				session.getBasicRemote().sendText(msg);
			} catch (IOException e) {
				e.printStackTrace();
				SESSIONS.remove(session);
			}
		}
	}

}
